package XTest.XPathGeneration.LogicTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class LogicTreeNode {
    public LogicTreeContextInfo contextInfo = new LogicTreeContextInfo();
    public XMLDatatypeComplexRecorder datatypeRecorder = new XMLDatatypeComplexRecorder();

    /**
     * If is calculable, contains the real value of evaluated context for the starred node
     * For a sequence, context contains the length of the sequence
     * For a node, context refers to the id number of the node
     */
    public String context = null;

    /**
     * Cached XPathExpr with no constant substitution.
     */
    public String XPathExpr = null;
    public abstract LogicTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException;

    public LogicTreeNode modifyToContainStarredNodeWithCheck(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        boolean contains = checkIfContainsStarredNode(starredNodeId);
        if(contains) return this;
        return modifyToContainStarredNode(starredNodeId);
    }

    public boolean checkIfContainsStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        String expr = getXPathExpression();
        List<Integer> resultList = contextInfo.mainExecutor.executeSingleProcessorGetIdList(contextInfo.XPathPrefix + "[" + expr + "]");
        return resultList.contains(starredNodeId);
    }


    /**
     *
     * @return The XPath expression represented by subtree of current information tree node.
     * e.g. for current XPath expression /A with a generated tree to express lower-case(@name) = "xx",
     * root node calling this method should return string "lower-case(@name) = "xx"".
     */
    public String getXPathExpression() {
        XPathExpr = getXPathExpression(false);
        return XPathExpr;
    }

    /**
     *
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return The XPath expression represented by subtree of current information tree node.
     */
    public String getXPathExpression(boolean returnConstant) {
        return getXPathExpression(returnConstant, null);
    }

    public abstract String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode);


    @Override
    public String toString() {
        return getXPathExpression();
    }

    /**
     * Cache the XPath expression calculated for current subtree if no constant replacement.
     * @param XPathExpr Calculated XPath expression
     * @param returnConstant XPath expression calculated w/o constant replacement
     */
    protected void cacheXPathExpression(String XPathExpr, boolean returnConstant) {
        if(!returnConstant) this.XPathExpr = XPathExpr;
    }

    /**
     * Calculate and cache the XPath expression calculated for current subtree if no constant replacement.
     * @param returnConstant
     */
    public void cacheXPathExpression(boolean returnConstant) {
        String XPathExpr = getXPathExpression();
        if(!returnConstant) this.XPathExpr = XPathExpr;
    }

    /**
     * Calculate and cache the XPath expression calculated for current subtree with no constant replacement.
     */
    public void cacheXPathExpression() {
        String XPathExpr = getXPathExpression();
        this.XPathExpr = XPathExpr;
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
}
