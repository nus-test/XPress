package XTest.XPathGeneration.LogicTree.InfomationTree;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.NotFunctionNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class InformationTreeNode extends LogicTreeNode {
    /**
     * Supplementary context only works for sequence of nodes, includes id of one random node in sequence
     */
    public String supplementaryContext = null;

    /**
     * If is sequence type, contains the length of sequence for the starred node
     */
    int length;

    /**
     * Only set to true when there is a context node in represented subtree.
     */
    public boolean containsContext = false;

    /**
     * If within subtree there is no context node or an ancestor node of the context node is
     * with calculated context value is set to true.
     */
    public boolean constantExpr = false;

    /**
     * Starred node id which is recorded in the unique context node.
     */
    protected int starredNodeId = -1;
    /**
     * If set to true unique context node refers to the starred node in context of XPath prefix.
     * Else refers to a derived sequence from the starred node with itself as the context.
     */
    public boolean selfContext = true;

    public List<InformationTreeNode> childList = new ArrayList<>();

    InformationTreeNode() {
        contextInfo = new InformationTreeContextInfo();
    }

    public String getXPathExpressionCheck(boolean returnConstant) {
        System.out.println("XPathExpr: " + XPathExpr);
        if(!containsContext && checkCalculableContext())
            return XMLDatatype.wrapExpression(context, datatypeRecorder.xmlDatatype);
        if(returnConstant && checkCalculableContext())
            return XMLDatatype.wrapExpression(context, datatypeRecorder.xmlDatatype);
        if(!returnConstant && XPathExpr != null)
            return XPathExpr;
        return null;
    }

    /**
     *
     * @return The calculation string which could compute the correct result for the starred node of
     * current information tree.
     */
    public String getCalculationString() throws DebugErrorException {
        String calculationString = "";
        if(constantExpr) calculationString = getXPathExpression(true);
        else if(!selfContext) {
            calculationString = "//*[id = \"" + starredNodeId + "\"]/" + getXPathExpression(true);
        } else if(childList.get(0).datatypeRecorder.xmlDatatype == XMLDatatype.NODE)
            calculationString = getCurrentLevelCalculationString();
        else calculationString = getXPathExpression(true);
        return calculationString;
    }

    public String getCurrentLevelCalculationString() {
        return null;
    }

    /**
     *
     * @return Context of subtree represented by current node.
     */
    String getContext() {
        return context;
    }

    boolean checkIfContainsStarredNode() throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        return checkIfContainsStarredNode(starredNodeId);
    }

    public boolean checkIfContainsStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        String expr = getXPathExpression();
        List<Integer> resultList = mainExecutor.executeSingleProcessorGetIdList(XPathPrefix + "[" + expr + "]");
        System.out.println("*************** Check if contains " + starredNodeId);
        System.out.println("XPath expression: " + XPathPrefix + "[" + expr + "]");
        System.out.println(resultList);
        return resultList.contains(starredNodeId);
    }

    @Override
    public InformationTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        boolean contains = checkIfContainsStarredNode(starredNodeId);
        if(contains) return this;
        NotFunctionNode newRoot = new NotFunctionNode();
        newRoot.fillContents(this);
        return newRoot;
    }

    /**
     * Every ancestor in the information tree of the unique context node should contain the necessary information
     * about the unique context node.
     * @param childNode
     */
    public void inheritContextChildInfo(InformationTreeNode childNode) {
        this.containsContext = childNode.containsContext;
        this.constantExpr = childNode.constantExpr;
        this.selfContext = childNode.selfContext;
        this.starredNodeId = childNode.starredNodeId;
        this.mainExecutor = childNode.mainExecutor;
        this.XPathPrefix = childNode.XPathPrefix;
        System.out.println("dada___________" + XPathPrefix + " " + (childNode instanceof InformationTreeContextNode));
    }

    public void calculateInfo() throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        String calculationString = getCalculationString();
        System.out.println("-------------> calculate info: " + calculationString);
        if(calculationString != null) {
            if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
                context = mainExecutor.executeSingleProcessor("count(" + calculationString + ")");
            }
            else context = mainExecutor.executeSingleProcessor(calculationString);
            if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE && datatypeRecorder.subDatatype == XMLDatatype.NODE) {
                Integer size = Integer.parseInt(context);
                Integer randomId = GlobalRandom.getInstance().nextInt(size) + 1;
                supplementaryContext = mainExecutor.executeSingleProcessor(calculationString + '[' +
                        randomId + "]/@id");
            }
        }
    }

    public Boolean checkCalculableContext() {
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) return false;
        if(datatypeRecorder.xmlDatatype == XMLDatatype.NODE) return false;
        return context != null;
    }
}
