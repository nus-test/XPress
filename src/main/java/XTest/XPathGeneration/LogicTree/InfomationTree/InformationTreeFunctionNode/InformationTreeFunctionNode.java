package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

import static XTest.StringUtils.getListString;

public abstract class InformationTreeFunctionNode extends InformationTreeNode {
    public String functionExpr;


    abstract public InformationTreeFunctionNode newInstance();

    /**
     * Fill the content parameters of current function node with given child node as context.
     * Is implemented with the best effort to fit parameters which is considered reasonable and if is boolean valued
     * to be evaluated true for given context.
     * @param childNode Given context.
     */
    abstract public void fillContents(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException;

    /**
     * Fill the content parameters of current function node with given child node as context.
     * The given context is either not evaluable or sequence, fill the remaining contents randomly.
     * @param childNode Given context.
     */
    abstract public void fillContentsRandom(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException;
    /**
     *
     * @return String expression of simplified function with current context.
     * e.g. upper-case("a") -> upper-case(.), current context represented by '.'.
     * Default for function with pattern "function(., param_a, param_b)"
     */
    public String getCurrentContextFunctionExpr() {
        return functionExpr + "(., " + getParametersXPathExpression(true, 1) + ")";
    }

    /**
     *
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return The XPath expression represented by subtree of current information tree node.
     */
    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode) {
        System.out.println("Why" + " " + returnConstant);
        String returnString = getXPathExpressionCheck(returnConstant);
        System.out.println("???? " + returnString);
        if(returnString != null) return returnString;
        returnString = getDefaultFunctionXPathExpression(returnConstant);
        String test = XPathExpr;
        cacheXPathExpression(returnString, returnConstant);
        System.out.println("()()() " + returnConstant + " " + returnString + " " + test + " " + XPathExpr);
        return returnString;
    }

    public String getDefaultFunctionXPathExpression(boolean returnConstant) {
        String builder = functionExpr + "(";
        builder += getParametersXPathExpression(returnConstant, 0);
        builder += ")";
        return builder;
    }

    /**
     *
     * @param returnConstant
     * @param startIndex
     * @return XPath expression of parameters in childList with start index separated by ',',
     * end index default set to list size.
     */
    public String getParametersXPathExpression(boolean returnConstant, int startIndex) {
        return getParametersXPathExpression(returnConstant, startIndex, childList.size());
    }

    /**
     *
     * @param returnConstant
     * @param startIndex
     * @param endIndex
     * @return XPath expression of parameters in childList with start index and end index separated by ','
     * (end index exclusive).
     */
    public String getParametersXPathExpression(boolean returnConstant, int startIndex, int endIndex) {
        String builder = "";
        for(int i = startIndex; i < endIndex; i ++) {
            if(i != startIndex) builder += ", ";
            builder += childList.get(i).getXPathExpression(returnConstant, null);
        }
        return builder;
    }

    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return checkContextAcceptability(childNode, childNode.datatypeRecorder);
    }

    abstract public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder);
}
