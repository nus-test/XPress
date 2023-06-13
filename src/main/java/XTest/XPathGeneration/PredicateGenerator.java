package XTest.XPathGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeGenerator;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeComparisonNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PredicateGenerator {
    MainExecutor mainExecutor;

    public PredicateGenerator(MainExecutor mainExecutor){
        this.mainExecutor = mainExecutor;
    }

    /**
     * Generates a predicate for selected node set by XPath prefix. e.g. XPath prefix = "/A",
     * generated result predicate string could be [@id = "1" and text() = "hello"].
     * @param XPathPrefix The prefix current candidate node set is selected by.
     * @param maxPhraseLength The number of individual information trees included in the generated predicate.
     * @param mixedContent If set to true, the nodes selected by XPathPrefix has different text types.
     * @param starredNode The starred node in current candidate node set which is required to be in the answer set.
     * @return The generated predicate string without square brackets and the result node set after predicate filtering.
     * @throws SQLException
     * @throws XMLDBException
     * @throws UnexpectedExceptionThrownException
     * @throws IOException
     * @throws SaxonApiException
     */
    public XPathResultListPair generatePredicate(String XPathPrefix, int maxPhraseLength,
                                                 boolean mixedContent, ContextNode starredNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        List<LogicTreeNode> rootList = new ArrayList<>();
        int phraseLength = GlobalRandom.getInstance().nextInt(maxPhraseLength) + 1;
        InformationTreeGenerator informationTreeGenerator = new InformationTreeGenerator(mainExecutor);
        for(int i = 0; i < phraseLength; i ++) {
            InformationTreeNode currentRoot = informationTreeGenerator.generateInformationTree(XPathPrefix, mixedContent, starredNode);
            rootList.add(currentRoot);
        }
        while(rootList.size() > 1) {
            int id = GlobalRandom.getInstance().nextInt(rootList.size());
            int id2 = id;
            while(id2 == id) {
                id2 = GlobalRandom.getInstance().nextInt(rootList.size());
            }
            LogicTreeComparisonNode newRoot = LogicTreeComparisonNode.getRandomCandidate().newInstance();
            newRoot.fillContents(rootList.get(id), rootList.get(id2));
            rootList.remove(id);
            if(id < id2) id2 -= 1;
            rootList.remove(id2);
            rootList.add(newRoot);
        }
        LogicTreeNode root = rootList.get(0);
        root = root.modifyToContainStarredNodeWithCheck(starredNode.id);
        String XPathExpression = XPathPrefix + "[" + root.getXPathExpression() + "]";
        List<ContextNode> selectedList = mainExecutor.executeSingleProcessorGetNodeList(XPathExpression);
        return new XPathResultListPair("[" + root.getXPathExpression() + "]", selectedList);
    }
}
