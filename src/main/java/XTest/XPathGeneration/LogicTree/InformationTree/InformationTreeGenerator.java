package XTest.XPathGeneration.LogicTree.InformationTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.DefaultListHashMap;
import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.XMLAtomic;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLNumeric;
import XTest.PrimitiveDatatype.XMLSimple;
import XTest.ReportGeneration.KnownBugs;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.BooleanFunctionNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager;
import XTest.XPathGeneration.XPathGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import javax.xml.xpath.XPath;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InformationTreeGenerator {
    public static DefaultListHashMap<XMLDatatype, InformationTreeNode> contextInformationTreeMap = new DefaultListHashMap<>();
    public static DefaultListHashMap<XMLDatatype, InformationTreeNode> sequenceInformationTreeMap = new DefaultListHashMap<>();

    MainExecutor mainExecutor;
    boolean complex;

    public InformationTreeGenerator(MainExecutor mainExecutor, boolean complex){
        this.mainExecutor = mainExecutor;
        this.complex = complex;
    }

    public InformationTreeGenerator(MainExecutor mainExecutor){
        this.mainExecutor = mainExecutor;
        complex = true;
    }


    /**
     * Generate an information tree which root node could be evaluated as boolean type.
     * @param XPathPrefix The prefix current candidate node set is selected by.
     * @param mixedContent If set to true, the nodes selected by XPathPrefix has different text types.
     * @param starredNode The starred node in current candidate node set which is required to be in the answer set.
     * @return Root node of the generated information tree (tree height is random within range).
     */
    public InformationTreeNode generateInformationTree(String XPathPrefix, boolean mixedContent, ContextNode starredNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        // First select the direct context for current evaluation.
        double prob = GlobalRandom.getInstance().nextDouble();

        InformationTreeContextNode contextNode = new InformationTreeContextNode();
        boolean selfContextFlag = false, containsContextFlag = true, constantExprFlag = false;
        contextInformationTreeMap = new DefaultListHashMap<>();
        boolean getXPathFlag = false;

        if(prob < 0.3 && complex) {
            String XPath;
            try {
                String pre = "//*[@id=\"" + starredNode.id + "\"]";
                XPath = new XPathGenerator(mainExecutor, false).generateXPath(pre,
                        List.of(starredNode), GlobalRandom.getInstance().nextInt(2) + 1);
                XPath = "." + XPath.substring(pre.length());
                contextNode.datatypeRecorder.setData(XMLDatatype.SEQUENCE, XMLDatatype.NODE, true);
                contextNode.setXPath(XPath);
                getXPathFlag = true;
            } catch(Exception ignore) {}
        }
        if(!getXPathFlag) {
            if (prob < 0.4 && starredNode.childWithLeafList.size() != 0 && !KnownBugs.exist) {
                String pathToLeaf = starredNode.getStrPathToSpecifiedLeafNode();
                contextNode.datatypeRecorder.setData(XMLDatatype.SEQUENCE, XMLDatatype.NODE, true);
                if (!pathToLeaf.endsWith("*"))
                    contextNode.datatypeRecorder.nodeMix = false;
                contextNode.setXPath(pathToLeaf);
            } else {
                // Select current node
                contextNode.datatypeRecorder.setData(XMLDatatype.NODE, null, mixedContent);
                selfContextFlag = true;
                contextNode.setXPath(".");
                contextNode.getContext().context = Integer.toString(starredNode.id);
            }
        }
        contextNode.setContextInfo(mainExecutor, XPathPrefix, starredNode.id, containsContextFlag,
                constantExprFlag, selfContextFlag);
        if(GlobalSettings.starNodeSelection)
            contextNode.calculateInfo();
        if(complex && GlobalRandom.getInstance().nextDouble() < 0.4) {
            int randomNum = 10;
            for(int i = 0; i < randomNum; i ++) {
                 InformationTreeContextNode subContextNode = new InformationTreeContextNode(contextNode);
                 int levelLimit = GlobalRandom.getInstance().nextInt(3) + 1;
                 if(levelLimit == 3 && GlobalRandom.getInstance().nextDouble() < 0.3) {
                     levelLimit = 1;
                 }
                 InformationTreeNode subRoot = buildInformationTree(subContextNode, levelLimit,
                         !GlobalSettings.starNodeSelection, GlobalSettings.starNodeSelection, false);
                 if(subRoot.datatypeRecorder.xmlDatatype.getValueHandler() instanceof XMLAtomic) {
                     contextInformationTreeMap.get(subRoot.datatypeRecorder.xmlDatatype).add(subRoot);
                 }
                if(subRoot.getCalculationString() == null) {
                    System.out.println("HUHUHUHUUHU");
                }
            }
        }
        // Build information tree from context node
        int levelLimit = GlobalRandom.getInstance().nextInt(complex ? 5 : 2);
        if(contextNode.getContextInfo().selfContext) levelLimit += 1;
        return buildBooleanInformationTree(contextNode, levelLimit);
    }

    /**
     * Build an information tree starting from informationTreeNode to grow upwards, with tree height to be controlled
     * around levelLimit. The resulting information tree is guaranteed to be evaluable as boolean.
     * @param informationTreeNode Starting node to build the information tree. Tree will be constructed upwards.
     * @param levelLimit Limitation of generated tree height, but is not guaranteed to strictly meet the limits.
     * @return The root node of the generated information tree.
     */
    public InformationTreeNode buildBooleanInformationTree(InformationTreeNode informationTreeNode, int levelLimit) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        InformationTreeNode root = buildInformationTree(informationTreeNode, levelLimit,
            !GlobalSettings.starNodeSelection, GlobalSettings.starNodeSelection, true);
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
            if(GlobalSettings.starNodeSelection)
                booleanRoot.fillContents(newRoot);
            else booleanRoot.fillContentsRandom(newRoot, false);
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
    public InformationTreeNode buildInformationTree(InformationTreeNode informationTreeNode, int levelLimit) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        return buildInformationTree(informationTreeNode, levelLimit, false, true, true);
    }

    /**
     * Build an information tree starting from informationTreeNode to grow upwards, with tree height limited to levelLimit.
     * The evaluation of the resulting information tree could result in any type.
     * @param informationTreeNode Starting node to build the information tree. Tree will be constructed upwards.
     * @param levelLimit Limitation of generated tree height.
     * @return The root node of the generated information tree.
     */
    public InformationTreeNode buildInformationTree(InformationTreeNode informationTreeNode, int levelLimit, Boolean random, Boolean calculate, Boolean acceptSequenceOperation) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        if(levelLimit == 0)  return informationTreeNode;

        // Update information tree node in to a new root
        InformationTreeNode newRoot;
        newRoot = InformationTreeFunctionNodeManager.getInstance()
                        .getRandomMatchingFunctionNodeWithContentAttached(
                                informationTreeNode, informationTreeNode.datatypeRecorder, random, calculate, acceptSequenceOperation);

        return buildInformationTree(newRoot, levelLimit - 1, random, calculate, acceptSequenceOperation);
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
    public InformationTreeNode aimedBooleanInformationTreeBuild(InformationTreeNode informationTreeNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        if(new BooleanFunctionNode().checkContextAcceptability(informationTreeNode)) {
            if(informationTreeNode.datatypeRecorder.xmlDatatype.getValueHandler() instanceof XMLNumeric) {
                BooleanFunctionNode newRoot = new BooleanFunctionNode();
                newRoot.fillContentsRandom(informationTreeNode, GlobalSettings.starNodeSelection);
                informationTreeNode = newRoot;
            }
            return informationTreeNode;
        }
        informationTreeNode = InformationTreeFunctionNodeManager.getInstance().getRandomMatchingFunctionNodeWithContentAttached(informationTreeNode, informationTreeNode.datatypeRecorder);
        return aimedBooleanInformationTreeBuild(informationTreeNode);
    }
}
