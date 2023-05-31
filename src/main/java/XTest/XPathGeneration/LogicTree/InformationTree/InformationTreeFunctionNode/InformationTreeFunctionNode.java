package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
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
     * Only to fill in the content parameters and other process such as for inheritance is not needed.
     * Default: has no parameters and output datatype is pre-set.
     * Is implemented with the best effort to fit parameters which is considered reasonable and if is boolean valued
     * to be evaluated true for given context.
     * @param childNode Given context.
     */
    protected void fillContentParameters(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
    }

    /**
     * Fill the content parameters of current function node with given child node as context.
     * Only to fill in the content parameters and other process such as for inheritance is not needed.
     * Default: has no parameters and output datatype is pre-set.
     * The given context is either not evaluable or sequence, fill the remaining contents randomly.
     * @param childNode Given context.
     */
    protected void fillContentParametersRandom(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
    }

    /**
     * Fill the content parameters of current function node with given child node as context.
     * Is implemented with the best effort to fit parameters which is considered reasonable and if is boolean valued
     * to be evaluated true for given context.
     * @param childNode Given context.
     */
    public void fillContents(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        fillContents(childNode, true);
    }

    public void fillContents(InformationTreeNode childNode, Boolean calculate) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        if(!childNode.checkValidContext())
            fillContentParametersRandom(childNode);
        else fillContentParameters(childNode);
        if(calculate) calculateInfo();
    }

    /**
     * Fill the content parameters of current function node with given child node as context.
     * The given context is either not evaluable or sequence, fill the remaining contents randomly.
     * @param childNode Given context.
     */
    public void fillContentsRandom(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        fillContentsRandom(childNode, true);
    }

    public void fillContentsRandom(InformationTreeNode childNode, Boolean calculate) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        fillContentParametersRandom(childNode);
        if(calculate) calculateInfo();
    }

    /**
     *
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return The XPath expression represented by subtree of current information tree node.
     */
    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        String returnString = getXPathExpressionCheck(returnConstant, parentNode, calculateString);
        if(returnString != null) return returnString;
        returnString = getDefaultFunctionXPathExpression(returnConstant, calculateString);
        cacheXPathExpression(returnString, returnConstant, calculateString);
        return returnString;
    }

    public String getDefaultFunctionXPathExpression(boolean returnConstant, boolean calculateString) throws DebugErrorException {
        String builder = functionExpr + "(";
        builder += getParametersXPathExpression(returnConstant, 0, calculateString);
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
    public String getParametersXPathExpression(boolean returnConstant, int startIndex, boolean calculateString) throws DebugErrorException {
        return getParametersXPathExpression(returnConstant, startIndex, childList.size(), calculateString);
    }

    /**
     *
     * @param returnConstant
     * @param startIndex
     * @param endIndex
     * @return XPath expression of parameters in childList with start index and end index separated by ','
     * (end index exclusive).
     */
    public String getParametersXPathExpression(boolean returnConstant, int startIndex, int endIndex, boolean calculateString) throws DebugErrorException {
        String builder = "";
        for(int i = startIndex; i < endIndex; i ++) {
            if(i != startIndex) builder += ", ";
            builder += childList.get(i).getXPathExpression(returnConstant, null, calculateString);
        }
        return builder;
    }

    abstract public Boolean checkContextAcceptability(InformationTreeNode childNode);
}
