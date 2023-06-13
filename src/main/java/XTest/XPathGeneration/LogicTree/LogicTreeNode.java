package XTest.XPathGeneration.LogicTree;

import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class LogicTreeNode {
    public LogicTreeContextInfo contextInfo = new LogicTreeContextInfo();
    public XMLDatatypeComplexRecorder datatypeRecorder = new XMLDatatypeComplexRecorder();

    public LogicTreeContext context;

    public LogicTreeContext getContext() {
        return context;
    }

    abstract public LogicTreeNode newInstance();

    /**
     * Cached XPathExpr with no constant substitution.
     */
    public String XPathExpr = null;
    public abstract LogicTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException;

    public LogicTreeNode modifyToContainStarredNodeWithCheck(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        boolean contains = checkIfContainsStarredNode(starredNodeId);
        if(contains) return this;
        return modifyToContainStarredNode(starredNodeId);
    }

    public boolean checkIfContainsStarredNode(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        String expr = getXPathExpression();
        //System.out.println("execute: " + contextInfo.XPathPrefix + "[" + expr + "]");
        List<Integer> resultList = contextInfo.mainExecutor.executeSingleProcessorGetIdList(contextInfo.XPathPrefix + "[" + expr + "]");
        return resultList.contains(starredNodeId);
    }


    /**
     *
     * @return The XPath expression represented by subtree of current information tree node.
     * e.g. for current XPath expression /A with a generated tree to express lower-case(@name) = "xx",
     * root node calling this method should return string "lower-case(@name) = "xx"".
     */
    public final String getXPathExpression() throws DebugErrorException {
        XPathExpr = getXPathExpression(false);
        return XPathExpr;
    }

    public final String getCalculationString() throws DebugErrorException {
        return getCalculationString(null, false);
    }

    /**
     *
     * @return The calculation string which could compute the correct result for the starred node of
     * current information tree. If current node has no impact on the
     * difference between XPath expression and calculation string returns null(default);
     */
    public final String getCalculationString(LogicTreeNode parentNode) throws DebugErrorException {
        return getCalculationString(parentNode, false);
    }

    /**
     *
     * @param checkImpact When set to true is called from getXPathExpression, else is not.
     * @return When checkImpact is set to false is the calculation string which could compute the correct result for the starred node of
     * current information tree. When checkImpact is set to true might return null if current node has no direct impact
     * on difference between XPath expression and calculation string.
     * @throws DebugErrorException
     */
    public String getCalculationString(LogicTreeNode parentNode, boolean checkImpact) throws DebugErrorException {
        if(!checkImpact) {
            return getXPathExpression(true, parentNode, true);
        }
        return null;
    }


    /**
     *
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return The XPath expression represented by subtree of current information tree node.
     */
    public final String getXPathExpression(boolean returnConstant) throws DebugErrorException {
        return getXPathExpression(returnConstant, null, false);
    }

    public final String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode) throws DebugErrorException {
        return getXPathExpression(returnConstant, parentNode, false);
    }

    public abstract String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException;

    /**
     * Cache the XPath expression calculated for current subtree if no constant replacement.
     * @param XPathExpr Calculated XPath expression
     * @param returnConstant XPath expression calculated w/o constant replacement
     */
    protected void cacheXPathExpression(String XPathExpr, boolean returnConstant, boolean calculateString) {
        if(!calculateString && !returnConstant) this.XPathExpr = XPathExpr;
    }

    /**
     * Every ancestor in the information tree of the unique context node should contain the necessary information
     * about the unique context node.
     * @param childNode
     */
    public void inheritContextChildInfo(LogicTreeNode childNode) {
        contextInfo = new LogicTreeContextInfo(childNode);
    }

    public LogicTreeContextInfo getContextInfo() {
        return contextInfo;
    }

    public abstract <T extends LogicTreeNode> List<T> getChildList();
}
