package XTest.XPathGeneration.LogicTree.InformationTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLNumeric;
import XTest.ReportGeneration.KnownBugs;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.BooleanFunctionNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class InformationTreeGenerator {
    MainExecutor mainExecutor;

    public InformationTreeGenerator(MainExecutor mainExecutor){
        this.mainExecutor = mainExecutor;
    }



    /**
     * Generate an information tree which root node could be evaluated as boolean type.
     * @param XPathPrefix The prefix current candidate node set is selected by.
     * @param mixedContent If set to true, the nodes selected by XPathPrefix has different text types.
     * @param starredNode The starred node in current candidate node set which is required to be in the answer set.
     * @return Root node of the generated information tree (tree height is random within range).
     */
    public InformationTreeNode generateInformationTree(String XPathPrefix, boolean mixedContent, ContextNode starredNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        // First select the direct context for current evaluation.
        double prob = GlobalRandom.getInstance().nextDouble();

        InformationTreeContextNode contextNode = new InformationTreeContextNode();
        boolean selfContextFlag, containsContextFlag = true, constantExprFlag = false;

        // Select a sequence
        if(prob < 0.3 && starredNode.childWithLeafList.size() != 0 && !KnownBugs.exist) {
            String pathToLeaf = starredNode.getStrPathToSpecifiedLeafNode();
            contextNode.datatypeRecorder.setData(XMLDatatype.SEQUENCE, XMLDatatype.NODE, true);
            if(!pathToLeaf.endsWith("*"))
                contextNode.datatypeRecorder.nodeMix = false;
            contextNode.setXPath(pathToLeaf);
            selfContextFlag = false;
        } else {
            // Select current node
            contextNode.datatypeRecorder.setData(XMLDatatype.NODE, null, mixedContent);
            selfContextFlag = true;
            contextNode.setXPath(".");
            contextNode.getContext().context = Integer.toString(starredNode.id);
        }
        contextNode.setContextInfo(mainExecutor, XPathPrefix, starredNode.id, containsContextFlag,
                constantExprFlag, selfContextFlag);
        contextNode.calculateInfo();
        // Build information tree from context node
        int levelLimit = GlobalRandom.getInstance().nextInt(5);
        if(contextNode.getContextInfo().selfContext) levelLimit += 1;
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
        InformationTreeNode root = buildInformationTree(informationTreeNode, levelLimit);
        double prob = GlobalRandom.getInstance().nextDouble();
        InformationTreeNode newRoot = null;
        if(prob < 0.5) {
            newRoot = booleanSubtreeSearch(root);
        }
        if(newRoot == null) {
            newRoot = aimedBooleanInformationTreeBuild(root);
        }
        if(newRoot.datatypeRecorder.xmlDatatype != XMLDatatype.BOOLEAN) {
            InformationTreeFunctionNode booleanRoot = new BooleanFunctionNode();
            booleanRoot.fillContents(newRoot);
            newRoot = booleanRoot;
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
        double prob = GlobalRandom.getInstance().nextDouble();
        // TODO: could loosen the constraint hear for attribute node cast through record of nodeMix?
//        if(prob < 0.4 && (informationTreeNode.datatypeRecorder.xmlDatatype != XMLDatatype.SEQUENCE ||
//                Integer.parseInt(informationTreeNode.context) != 0)
//            && !(informationTreeNode instanceof AttributeFunctionNode)) {
//            XMLDatatypeComplexRecorder recorder = InformationTreeFunctionNodeManager.getInstance()
//                    .getRandomTargetedDatatypeRecorder(informationTreeNode.datatypeRecorder);
//            CastFunctionNode castedInformationTreeNode = new CastFunctionNode();
//            castedInformationTreeNode.fillContentsSpecificAimedType(informationTreeNode, recorder);
//            informationTreeNode = castedInformationTreeNode;
//        }
        newRoot = InformationTreeFunctionNodeManager.getInstance()
                .getRandomMatchingFunctionNodeWithContentAttached(informationTreeNode, informationTreeNode.datatypeRecorder);

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
            if(informationTreeNode.datatypeRecorder.xmlDatatype.getValueHandler() instanceof XMLNumeric) {
                BooleanFunctionNode newRoot = new BooleanFunctionNode();
                newRoot.fillContentsRandom(informationTreeNode);
                informationTreeNode = newRoot;
            }
            return informationTreeNode;
        }
        informationTreeNode = InformationTreeFunctionNodeManager.getInstance().getRandomMatchingFunctionNodeWithContentAttached(informationTreeNode, informationTreeNode.datatypeRecorder);
        return aimedBooleanInformationTreeBuild(informationTreeNode);
    }
}
