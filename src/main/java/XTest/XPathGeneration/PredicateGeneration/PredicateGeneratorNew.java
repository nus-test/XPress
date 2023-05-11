package XTest.XPathGeneration.PredicateGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.ReportGeneration.KnownBugs;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeContextNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeAndComparisonNode;
import XTest.XPathGeneration.LogicTree.LogicTreeComparisonNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import XTest.XPathGeneration.XPathResultListPair;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PredicateGeneratorNew {
    MainExecutor mainExecutor;

    public PredicateGeneratorNew(MainExecutor mainExecutor){
        this.mainExecutor = mainExecutor;
    }

    public XPathResultListPair generatePredicate(String XPathPrefix, int maxPhraseLength,
                                                 boolean mixedContent, ContextNode starredNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        String currentNodeIdentifier = "[@id=\"" + starredNode.id + "\"]";
        List<LogicTreeNode> rootList = null;
        int phraseLength = GlobalRandom.getInstance().nextInt(maxPhraseLength) + 1;
        for(int i = 0; i < phraseLength; i ++) {
            InformationTreeNode currentRoot = generateInformationTree(XPathPrefix, mixedContent, starredNode);
            rootList.add(currentRoot);
        }
        while(rootList.size() > 1) {
            int id = GlobalRandom.getInstance().nextInt(rootList.size());
            int id2 = id;
            while(id2 == id) {
                id2 = GlobalRandom.getInstance().nextInt(rootList.size());
            }
            LogicTreeComparisonNode newRoot = LogicTreeComparisonNode.getRandomCandidate();
            newRoot.fillContents(rootList.get(id), rootList.get(id2));
            rootList.remove(id);
            rootList.remove(id2);
            rootList.add(newRoot);
        }
        LogicTreeNode root = rootList.get(0);
        root = root.modifyToContainStarredNode(mainExecutor, starredNode.id);
        String XPathExpression = XPathPrefix + "[" + root.getXPathExpression() + "]";
        List<ContextNode> selectedList = mainExecutor.executeSingleProcessorGetNodeList(XPathExpression);
        return new XPathResultListPair(XPathExpression, selectedList);
    }


    public InformationTreeNode generateInformationTree(String XPathPrefix, boolean mixedContent, ContextNode starredNode) {
        // First select the direct context for current evaluation.
        double prob = GlobalRandom.getInstance().nextDouble();

        InformationTreeContextNode contextNode = new InformationTreeContextNode();
        contextNode.XPathPrefix = XPathPrefix;
        contextNode.setStarredNodeId(starredNode.id);
        // Select a sequence
        if(prob < 0.3 && starredNode.childWithLeafList.size() != 0 && !KnownBugs.exist) {
            String pathToLeaf = starredNode.getStrPathToSpecifiedLeafNode();
            contextNode.dataTypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            contextNode.dataTypeRecorder.subDatatype = XMLDatatype.NODE;
            if(!pathToLeaf.endsWith("*"))
                contextNode.dataTypeRecorder.nodeMix = false;
            contextNode.setXPath(pathToLeaf);
            contextNode.setSelfContextFlag(false);

        } else {
            // Select current node
            contextNode.dataTypeRecorder.xmlDatatype = XMLDatatype.NODE;
            contextNode.dataTypeRecorder.nodeMix = mixedContent;
            contextNode.setSelfContextFlag(true);
        }
        return null;
    }

    public InformationTreeNode buildBooleanInformationTree(InformationTreeNode informationTreeNode, int levelLimit) {
        return null;
    }

    public InformationTreeNode buildInformationTree(InformationTreeNode informationTreeNode, int levelLimit) {
        if(levelLimit == 0)  return informationTreeNode;

        // Update information tree node in to a new root
        

        return buildInformationTree(informationTreeNode, levelLimit - 1);
    }
}
