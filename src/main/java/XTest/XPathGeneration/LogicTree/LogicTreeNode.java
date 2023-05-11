package XTest.XPathGeneration.LogicTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.UnexpectedExceptionThrownException;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public abstract class LogicTreeNode {
    /**
     * Cached XPathExpr with no constant substitution.
     */
    protected String XPathExpr = null;
    public XMLDatatypeComplexRecorder dataTypeRecorder = new XMLDatatypeComplexRecorder();
    public abstract LogicTreeNode modifyToContainStarredNode(MainExecutor mainExecutor, int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException;

    /**
     *
     * @return The XPath expression represented by subtree of current information tree node.
     * e.g. for current XPath expression /A with a generated tree to express lower-case(@name) = "xx",
     * root node calling this method should return string "lower-case(@name) = "xx"".
     */
    public String getXPathExpression() {
        if(XPathExpr != null) return XPathExpr;
        XPathExpr = getXPathExpression(false);
        return XPathExpr;
    }

    /**
     *
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return The XPath expression represented by subtree of current information tree node.
     */
    public String getXPathExpression(boolean returnConstant) { return null; }

    @Override
    public String toString() {
        return getXPathExpression();
    }

    /**
     * Cache the XPath expression calculated for current subtree if no constant replacement.
     * @param XPathExpr Calculated XPath expression
     * @param returnConstant XPath expression calculated w/o constant replacement
     */
    void cacheXPathExpression(String XPathExpr, boolean returnConstant) {
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
}
