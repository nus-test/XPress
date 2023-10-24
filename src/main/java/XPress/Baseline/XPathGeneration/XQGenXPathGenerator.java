package XPress.Baseline.XPathGeneration;

import XPress.DatabaseExecutor.MainExecutor;
import XPress.GlobalRandom;
import XPress.GlobalSettings;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XMLGeneration.ContextNode;
import XPress.XPathGeneration.XPathGenerator;
import XPress.XPathGeneration.XPathResultListPair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XQGenXPathGenerator implements XPathGenerator {
    MainExecutor mainExecutor;

    public XQGenXPathGenerator(MainExecutor mainExecutor) {
        this.mainExecutor = mainExecutor;
    }

    public String getXPath(int depth) {
        return getXPathSectionDivided(depth).getRight();
    }

    public Pair<List<Pair<Integer, Integer>>, String> getXPathSectionDivided
            (int depth) {
        return generateXPathSectionDivided(Pair.of(new ArrayList<>(), ""),
                new XPathResultListPair("", null), depth);
    }

    public Pair<List<Pair<Integer, Integer>>, String> generateXPathSectionDivided
            (Pair<List<Pair<Integer, Integer>>, String> currentList,
             XPathResultListPair starterBuildPair, int depth) {
        if(depth == 0)
            return currentList;
        ContextNode node = currentList.getRight().length() > 0 ?
                GlobalRandom.getInstance().getRandomFromList(starterBuildPair.contextNodeList) : null;
        if(node != null && node.childList.size() == 0) {
            return currentList;
        }
        double prob = GlobalRandom.getInstance().nextDouble();
        ContextNode child = GlobalRandom.getInstance().getRandomFromList(
                node == null ? executeSingleProcessorGetNodeList("/*") : node.childList);
        String XPath;
        if(prob < 0.75) {
            XPath = starterBuildPair.XPath + "/";
        } else {
            XPath = starterBuildPair.XPath + "//";
        }
        prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.75) {
            XPath += child.tagName;
        } else {
            XPath += "*";
        }
        List<ContextNode> resultNodes = new ArrayList<>();
        resultNodes = executeSingleProcessorGetNodeList(XPath);
        if(resultNodes == null) return currentList;
        ContextNode selected = GlobalRandom.getInstance().getRandomFromList(resultNodes);
        prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.2 && selected.childList.size() > 0) {
            int num = GlobalRandom.getInstance().nextInt(3) + 1;
            for(int i = 1; i <= num; i ++) {
                XPath += "[./" + GlobalRandom.getInstance().getRandomFromList(selected.childList).tagName + "]";
            }
        }
        int l = starterBuildPair.XPath.length();
        resultNodes = executeSingleProcessorGetNodeList(XPath);
        if(resultNodes == null) return currentList;
        currentList.getLeft().add(Pair.of(l, XPath.length()));
        return generateXPathSectionDivided(Pair.of(currentList.getLeft(), XPath),
                new XPathResultListPair(XPath, resultNodes), depth - 1);
    }

    public List<ContextNode> executeSingleProcessorGetNodeList(String XPath) {
        List<ContextNode> resultNodes;
        try {
            resultNodes = mainExecutor.executeSingleProcessorGetNodeList(XPath, GlobalSettings.defaultDBName);
        } catch (Exception e) {
            System.out.println("XQGenXPathGenerator");
            System.out.println("Error occurred while executing " + XPath + " with " + GlobalSettings.defaultDBName);
            System.out.println("XML: " + mainExecutor.currentContext);
            return null;
        }
        return resultNodes;
    }
}
