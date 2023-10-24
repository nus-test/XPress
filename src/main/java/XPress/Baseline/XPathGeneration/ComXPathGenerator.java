package XPress.Baseline.XPathGeneration;

import XPress.DatabaseExecutor.MainExecutor;
import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.DatatypeControl.XMLComparable;
import XPress.GlobalRandom;
import XPress.GlobalSettings;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XMLGeneration.AttributeNode;
import XPress.XMLGeneration.ContextNode;
import XPress.XMLGeneration.ElementNode;
import XPress.XPathGeneration.XPathGenerator;
import XPress.XPathGeneration.XPathResultListPair;
import org.apache.commons.lang3.tuple.Pair;

import javax.naming.Context;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComXPathGenerator implements XPathGenerator {
    MainExecutor mainExecutor;

    public ComXPathGenerator(MainExecutor mainExecutor) {
        this.mainExecutor = mainExecutor;
    }

    public String getXPath(int depth) {
        return getXPathSectionDivided(depth).getRight();
    }

    public XPathResultListPair getSimplePath(ContextNode contextNode, String XPath, int depth) {
        if(depth == 0) return new XPathResultListPair(XPath, List.of(contextNode));
        if(contextNode.childList.size() == 0) return new XPathResultListPair(XPath, List.of(contextNode));
        ContextNode node = GlobalRandom.getInstance().getRandomFromList(contextNode.childList);
        return getSimplePath(node, XPath + "/" + node.tagName, depth - 1);
    }

    public boolean getDGLeafFlag(String XPath) {
        List<ContextNode> resultNodes = executeSingleProcessorGetNodeList(XPath);
        boolean flag = true;
        for(ContextNode node: resultNodes) {
            if (node.childList.size() > 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public String getPredicate(ContextNode contextNode, Boolean flag) {
        XPathResultListPair pair = getSimplePath(contextNode, "", GlobalRandom.getInstance().nextInt(3) + 1);
        double prob = GlobalRandom.getInstance().nextDouble();
        ElementNode node = pair.contextNodeList.get(0);
        if(prob < 0.3) {
            node = GlobalRandom.getInstance().getRandomFromList(
                    pair.contextNodeList.get(0).attributeList);
            pair.XPath += "/@" + node.tagName;
            flag = true;
        }
        if(node.dataType instanceof XMLComparable && flag) {
            String value = XMLDatatype.wrapExpression(node.dataType.getValueHandler().getValue(),
                    node.dataType);
            ArrayList<String> ops = new ArrayList<>(List.of("<", ">", ">=", "<=", "=", "!="));
            if(node.dataType instanceof XMLDouble)
                ops = new ArrayList<>(List.of("<", ">"));
            String op = GlobalRandom.getInstance().getRandomFromList(ops);
            return pair.XPath + " " + op + " " + value;
        }
        return pair.XPath;
    }

    public Pair<List<Pair<Integer, Integer>>, String> getXPathSectionDivided
            (int depth) {
        return generateXPathSectionDivided(Pair.of(new ArrayList<>(), ""),
                new XPathResultListPair("", new ArrayList<>()), "", depth);
    }

    public Pair<List<Pair<Integer, Integer>>, String> generateXPathSectionDivided
            (Pair<List<Pair<Integer, Integer>>, String> currentList,
             XPathResultListPair starterBuildPair, String XPathWOPred, int depth) {
        if(depth == 0)
            return currentList;
        //System.out.println("here: " + currentList.getRight() + " " + XPathWOPred + " " + starterBuildPair.contextNodeList.size());
        ContextNode node = currentList.getRight().length() > 0 ?
                GlobalRandom.getInstance().getRandomFromList(starterBuildPair.contextNodeList) : null;
        if(node != null && node.childList.size() == 0) {
            return currentList;
        }
        double prob = GlobalRandom.getInstance().nextDouble();
        ContextNode child = GlobalRandom.getInstance().getRandomFromList(
                node == null ? executeSingleProcessorGetNodeList("/*") : node.childList);
        //System.out.println(child.id);
        String XPath;
        int l = starterBuildPair.XPath.length();
        if(prob < 0.75) {
            XPath = starterBuildPair.XPath + "/";
            XPathWOPred += "/";
        } else {
            XPath = starterBuildPair.XPath + "//";
            XPathWOPred += "//";
        }
        prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.75) {
            XPath += child.tagName;
            XPathWOPred += child.tagName;
        } else {
            XPath += "*";
            XPathWOPred += "*";
        }
        List<ContextNode> resultNodes = executeSingleProcessorGetNodeList(XPathWOPred);
        //System.out.println("??? " + XPathWOPred + " " + currentList.getRight() + " " + mainExecutor.currentContext);
        if(resultNodes == null) return currentList;
        ContextNode selected = GlobalRandom.getInstance().getRandomFromList(resultNodes);
        prob = GlobalRandom.getInstance().nextDouble();
        Boolean flag = getDGLeafFlag(XPathWOPred);
        if(prob < 0.2 && selected.childList.size() > 0) {
            int num = GlobalRandom.getInstance().nextInt(3) + 1;
            for(int i = 1; i <= num; i ++) {
                XPath += "[" + getPredicate(selected, flag) + "]";
            }
        }
        currentList.getLeft().add(Pair.of(l, XPath.length()));
        return generateXPathSectionDivided(Pair.of(currentList.getLeft(), XPath),
                new XPathResultListPair(XPath, resultNodes), XPathWOPred, depth - 1);
    }

    public List<ContextNode> executeSingleProcessorGetNodeList(String XPath) {
        List<ContextNode> resultNodes;
        try {
            resultNodes = mainExecutor.executeSingleProcessorGetNodeList(XPath, GlobalSettings.defaultDBName);
        } catch (Exception e) {
            System.out.println("ComXPathGenerator");
            System.out.println("Error occurred while executing " + XPath + " with " + GlobalSettings.defaultDBName);
            System.out.println("XML: " + mainExecutor.currentContext);
            return null;
        }
        return resultNodes;
    }
}
