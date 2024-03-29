package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XPress.XPathGeneration.LogicTree.LogicTreeNode;

import java.io.IOException;
import java.sql.SQLException;

import static XPress.StringUtils.getListString;

public abstract class InformationTreeFunctionNode extends InformationTreeNode {
    public String functionExpr;

    @Override
    abstract public InformationTreeFunctionNode newInstance();

    protected boolean fillContentParameterBySubRoot(XMLDatatype datatype) {
        InformationTreeNode subRoot = InformationTreeFunctionNodeManager.getInstance().getNodeWithSimpleType(datatype, true);
        if(subRoot != null) {
            childList.add(subRoot);
            return true;
        }
        return false;
    }

    /**
     * Fill the content parameters of current function node with given child node as context.
     * Only to fill in the content parameters and other process such as for inheritance is not needed.
     * Default: has no parameters and output datatype is pre-set.
     * Is implemented with the best effort to fit parameters which is considered reasonable and if is boolean valued
     * to be evaluated true for given context.
     * @param childNode Given context.
     */
    protected void fillContentParameters(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
    }

    /**
     * Fill the content parameters of current function node with given child node as context.
     * Only to fill in the content parameters and other process such as for inheritance is not needed.
     * Default: has no parameters and output datatype is pre-set.
     * The given context is either not evaluable or sequence, fill the remaining contents randomly.
     * @param childNode Given context.
     */
    protected void fillContentParametersRandom(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
    }

    /**
     * Fill the content parameters of current function node with given child node as context.
     * Is implemented with the best effort to fit parameters which is considered reasonable and if is boolean valued
     * to be evaluated true for given context.
     * @param childNode Given context.
     */
    public void fillContents(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        fillContents(childNode, true);
    }

    public void fillContents(InformationTreeNode childNode, Boolean calculate) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
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
    public void fillContentsRandom(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        fillContentsRandom(childNode, true);
    }

    public void fillContentsRandom(InformationTreeNode childNode, Boolean calculate) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
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
