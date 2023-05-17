package XTest.XPathGeneration.LogicTree.InfomationTree;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode.InformationTreeDirectContentFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeNotFunctionNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class InformationTreeNode extends LogicTreeNode {

    public XMLDatatypeComplexRecorder datatypeRecorder = new XMLDatatypeComplexRecorder();

    /**
     * If is calculable, contains the real value of evaluated context for the starred node
     * For a sequence, context contains the length of the sequence
     * For a node, context refers to the id number of the node
     */
    public String context = null;

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
    public boolean containsContextConstant = false;

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

    public String getXPathExpressionCheck(boolean returnConstant) {
        if(!containsContext && context != null)
            return XMLDatatype.wrapExpression(context, datatypeRecorder.xmlDatatype);
        if(returnConstant && context != null)
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
    String getCalculationString() throws DebugErrorException {
        String calculationString = "";
        if(containsContextConstant) calculationString = getXPathExpression(true);
        else if(!selfContext) {
            calculationString = "//*[id = \"" + starredNodeId + "\"]/" + getXPathExpression(true);
        } else if(this instanceof InformationTreeDirectContentFunctionNode)
            calculationString = ((InformationTreeDirectContentFunctionNode) this).getCurrentLevelCalculationString();
        else calculationString = getXPathExpression(false);
        return calculationString;
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

    boolean checkIfContainsStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        String expr = getXPathExpression();
        List<Integer> resultList = mainExecutor.executeSingleProcessorGetIdList(expr);
        return resultList.contains(starredNodeId);
    }

    @Override
    public InformationTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        boolean contains = checkIfContainsStarredNode(starredNodeId);
        if(contains) return this;
        InformationTreeNotFunctionNode newRoot = new InformationTreeNotFunctionNode();
        newRoot.fillContents(this);
        return newRoot;
    }

    /**
     * Every ancestor in the information tree of the unique context node should contain the necessary information
     * about the unique context node.
     * @param childNode
     */
    public void inheritContextChildInfo(InformationTreeNode childNode) {
        this.containsContextConstant = childNode.containsContextConstant;
        this.selfContext = childNode.selfContext;
        this.starredNodeId = childNode.starredNodeId;
    }
}
