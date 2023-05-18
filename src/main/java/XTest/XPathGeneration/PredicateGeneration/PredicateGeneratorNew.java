package XTest.XPathGeneration.PredicateGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.ReportGeneration.KnownBugs;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeContextNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.BooleanFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.ImplicitCastFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeComparisonNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import XTest.XPathGeneration.XPathResultListPair;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
                                                 boolean mixedContent, ContextNode starredNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        String currentNodeIdentifier = "[@id=\"" + starredNode.id + "\"]";
        List<LogicTreeNode> rootList = new ArrayList<>();
        int phraseLength = GlobalRandom.getInstance().nextInt(maxPhraseLength) + 1;
        for(int i = 0; i < phraseLength; i ++) {
            InformationTreeNode currentRoot = generateInformationTree(XPathPrefix, mixedContent, starredNode);
            rootList.add(currentRoot);
            System.out.println("--------------------------->>>>>>>>>");
            System.out.println("Current root: " + currentRoot.getXPathExpression() + " " + currentRoot.getClass());
            System.out.println("--------------------------->>>>>>>>>");
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
        System.out.println("final generated: " + XPathExpression);
        List<ContextNode> selectedList = mainExecutor.executeSingleProcessorGetNodeList(XPathExpression);
        return new XPathResultListPair("[" + root.getXPathExpression() + "]", selectedList);
    }

    /**
     * Generate a information tree which root node could be evaluated as boolean type.
     * @param XPathPrefix The prefix current candidate node set is selected by.
     * @param mixedContent If set to true, the nodes selected by XPathPrefix has different text types.
     * @param starredNode The starred node in current candidate node set which is required to be in the answer set.
     * @return Root node of the generated information tree (tree height is random within range).
     */
    public InformationTreeNode generateInformationTree(String XPathPrefix, boolean mixedContent, ContextNode starredNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        // First select the direct context for current evaluation.
        double prob = GlobalRandom.getInstance().nextDouble();

        InformationTreeContextNode contextNode = new InformationTreeContextNode();
        contextNode.XPathPrefix = XPathPrefix;
        contextNode.setStarredNodeId(starredNode.id);
        contextNode.mainExecutor = mainExecutor;
        contextNode.containsContext = true;

        System.out.println("Entering here ===================> Starred node Id: " + starredNode.id);

        // Select a sequence
        if(prob < 0.3 && starredNode.childWithLeafList.size() != 0 && !KnownBugs.exist) {
            String pathToLeaf = starredNode.getStrPathToSpecifiedLeafNode();
            contextNode.datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            contextNode.datatypeRecorder.subDatatype = XMLDatatype.NODE;
            if(!pathToLeaf.endsWith("*"))
                contextNode.datatypeRecorder.nodeMix = false;
            contextNode.setXPath(pathToLeaf);
            contextNode.setSelfContextFlag(false);
            System.out.println("Selected sequence: ");
            System.out.println(pathToLeaf);
        } else {
            // Select current node
            contextNode.datatypeRecorder.xmlDatatype = XMLDatatype.NODE;
            contextNode.datatypeRecorder.nodeMix = mixedContent;
            contextNode.setSelfContextFlag(true);
            contextNode.setXPath(".");
            contextNode.context = Integer.toString(starredNode.id);
            System.out.println("Selected node");
        }
        contextNode.calculateInfo();
        // Build information tree from context node
        int levelLimit = GlobalRandom.getInstance().nextInt(5);
        if(contextNode.selfContext) levelLimit += 1;
        System.out.println("|||||||||||||||||||||||||||||||||||" + contextNode.XPathPrefix);
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
    public InformationTreeNode buildBooleanInformationTree(InformationTreeNode informationTreeNode, int levelLimit) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        System.out.println("What " + informationTreeNode.XPathPrefix);
        InformationTreeNode root = buildInformationTree(informationTreeNode, levelLimit);
        double prob = GlobalRandom.getInstance().nextDouble();
        InformationTreeNode newRoot = null;
        if(prob < 0.5) {
            newRoot = booleanSubtreeSearch(root);
        }
        if(newRoot == null) {
            newRoot = aimedBooleanInformationTreeBuild(root);
        }
        return newRoot;
    }

    /**
     * Build an information tree starting from informationTreeNode to grow upwards, with tree height limited to levelLimit.
     * The evaluation of the resulting information tree could result in any type.
     * @param informationTreeNode Starting node to build the information tree. Tree will be constructed upwards.
     * @param levelLimit Limitation of generated tree height.
     * @return The root node of the generated information tree.
     */
    public InformationTreeNode buildInformationTree(InformationTreeNode informationTreeNode, int levelLimit) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        if(levelLimit == 0)  return informationTreeNode;

        // Update information tree node in to a new root
        InformationTreeNode newRoot;
        System.out.println("+++++++++ Build Information Tree " + informationTreeNode.XPathPrefix);
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.4) {
            XMLDatatypeComplexRecorder recorder = InformationTreeFunctionNodeManager.getInstance()
                    .getRandomTargetedDatatypeRecorder(informationTreeNode.datatypeRecorder);
            ImplicitCastFunctionNode castedInformationTreeNode = new ImplicitCastFunctionNode();
            castedInformationTreeNode.fillContentsSpecificAimedType(informationTreeNode, recorder);
            informationTreeNode = castedInformationTreeNode;
            System.out.println("Casted to datatype of : " + recorder);
        }
        newRoot = InformationTreeFunctionNodeManager.getInstance()
                .getRandomMatchingFunctionNodeWithContentAttached(informationTreeNode, informationTreeNode.datatypeRecorder);
        System.out.println("Matched with function node " + newRoot.getClass() + " " + informationTreeNode.XPathPrefix);
        newRoot.calculateInfo();
        System.out.println("current root XPath expr state: " + newRoot.XPathExpr);
        if(newRoot.datatypeRecorder.xmlDatatype != XMLDatatype.SEQUENCE && newRoot.datatypeRecorder.subDatatype != XMLDatatype.NODE)
            newRoot.containsContextConstant = true;
        return buildInformationTree(newRoot, levelLimit - 1);
    }

    /**
     * Search in the given information tree for a subtree which includes the context node and ends in a value
     * which could be evaluated as boolean.
     * @param informationTreeNode The root of the information tree to be searched for.
     * @return The root node of the subtree which could be evaluated as boolean (If none is found null is returned).
     */
    public InformationTreeNode booleanSubtreeSearch(InformationTreeNode informationTreeNode) {
        if(informationTreeNode.childList.size() == 0) {
            return null;
        }
        if(new BooleanFunctionNode().checkContextAcceptability(informationTreeNode))
            return informationTreeNode;
        return booleanSubtreeSearch(informationTreeNode.childList.get(0));
    }

    /**
     * Build an information tree which results in type evaluable to boolean with limited levels added.
     * @param informationTreeNode
     * @return
     * @throws SQLException
     * @throws XMLDBException
     * @throws UnexpectedExceptionThrownException
     * @throws IOException
     * @throws SaxonApiException
     * @throws DebugErrorException
     */
    public InformationTreeNode aimedBooleanInformationTreeBuild(InformationTreeNode informationTreeNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        if(new BooleanFunctionNode().checkContextAcceptability(informationTreeNode)) {
            if(informationTreeNode.datatypeRecorder.xmlDatatype != XMLDatatype.BOOLEAN) {
                ImplicitCastFunctionNode newRoot = new ImplicitCastFunctionNode();
                newRoot.fillContentsSpecificAimedType(informationTreeNode, XMLDatatype.BOOLEAN);
                newRoot.calculateInfo();
                informationTreeNode = newRoot;
            }
            return informationTreeNode;
        }
        informationTreeNode = InformationTreeFunctionNodeManager.getInstance().getRandomMatchingFunctionNodeWithContentAttached(informationTreeNode, informationTreeNode.datatypeRecorder);
        return aimedBooleanInformationTreeBuild(informationTreeNode);
    }
}
