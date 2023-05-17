package XTest.XPathGeneration.PredicateGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.ReportGeneration.KnownBugs;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeContextNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
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
        root = root.modifyToContainStarredNode(starredNode.id);
        String XPathExpression = XPathPrefix + "[" + root.getXPathExpression() + "]";
        List<ContextNode> selectedList = mainExecutor.executeSingleProcessorGetNodeList(XPathExpression);
        return new XPathResultListPair(XPathExpression, selectedList);
    }

    /**
     * Generate a information tree which root node could be evaluated as boolean type.
     * @param XPathPrefix The prefix current candidate node set is selected by.
     * @param mixedContent If set to true, the nodes selected by XPathPrefix has different text types.
     * @param starredNode The starred node in current candidate node set which is required to be in the answer set.
     * @return Root node of the generated information tree (tree height is random within range).
     */
    public InformationTreeNode generateInformationTree(String XPathPrefix, boolean mixedContent, ContextNode starredNode) {
        // First select the direct context for current evaluation.
        double prob = GlobalRandom.getInstance().nextDouble();

        InformationTreeContextNode contextNode = new InformationTreeContextNode();
        contextNode.XPathPrefix = XPathPrefix;
        contextNode.setStarredNodeId(starredNode.id);
        // Select a sequence
        if(prob < 0.3 && starredNode.childWithLeafList.size() != 0 && !KnownBugs.exist) {
            String pathToLeaf = starredNode.getStrPathToSpecifiedLeafNode();
            contextNode.datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            contextNode.datatypeRecorder.subDatatype = XMLDatatype.NODE;
            if(!pathToLeaf.endsWith("*"))
                contextNode.datatypeRecorder.nodeMix = false;
            contextNode.setXPath(pathToLeaf);
            contextNode.setSelfContextFlag(false);

        } else {
            // Select current node
            contextNode.datatypeRecorder.xmlDatatype = XMLDatatype.NODE;
            contextNode.datatypeRecorder.nodeMix = mixedContent;
            contextNode.setSelfContextFlag(true);
        }

        // Build information tree from context node
        int levelLimit = GlobalRandom.getInstance().nextInt(5);
        if(contextNode.selfContext == true) levelLimit += 1;
        InformationTreeNode root = buildBooleanInformationTree(contextNode, levelLimit);
        return root;
    }

    /**
     * Build an information tree starting from informationTreeNode to grow upwards, with tree height to be controlled
     * around levelLimit. The resulting information tree is guaranteed to be evaluable as boolean.
     * @param informationTreeNode Starting node to build the information tree. Tree will be constructed upwards.
     * @param levelLimit Limitation of generated tree height, but is not guaranteed to strictly meet the limits.
     * @return The root node of the generated information tree.
     */
    public InformationTreeNode buildBooleanInformationTree(InformationTreeNode informationTreeNode, int levelLimit) {
        InformationTreeNode root = buildInformationTree(informationTreeNode, levelLimit);
        return null;
    }

    /**
     * Build an information tree starting from informationTreeNode to grow upwards, with tree height limited to levelLimit.
     * The evaluation of the resulting information tree could result in any type.
     * @param informationTreeNode Starting node to build the information tree. Tree will be constructed upwards.
     * @param levelLimit Limitation of generated tree height.
     * @return The root node of the generated information tree.
     */
    public InformationTreeNode buildInformationTree(InformationTreeNode informationTreeNode, int levelLimit) {
        if(levelLimit == 0)  return informationTreeNode;

        // Update information tree node in to a new root
        double prob = GlobalRandom.getInstance().nextDouble();

        InformationTreeNode newRoot;

        XMLDatatypeComplexRecorder recorder = InformationTreeFunctionNodeManager.getRandomTargetedDatatypeRecorder(informationTreeNode.datatypeRecorder);
        newRoot = InformationTreeFunctionNodeManager.getRandomMatchingFunctionNodeWithContentAttached(informationTreeNode, recorder);

        return buildInformationTree(newRoot, levelLimit - 1);
    }
}