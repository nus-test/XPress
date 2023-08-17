package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XPress.XPathGeneration.LogicTree.LogicTreeComparisonNode;
import XPress.XPathGeneration.LogicTree.LogicTreeNode;

import java.io.IOException;
import java.sql.SQLException;

@FunctionV3
public class CastableFunctionNode extends BinaryOperatorFunctionNode {
    String transformedDatatypeName;
    public CastableFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        functionExpr = "castable as";
    }

    @Override
    public CastableFunctionNode newInstance() {
        return new CastableFunctionNode();
    }

    /**
     * Same as fillContentsRandom, implicitly cast the child node into a random castable type.
     * @param childNode Given context.
     */
    @Override
    protected void fillContentParameters(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        fillContentParametersRandom(childNode);
    }

    /**
     * Implicitly cast the child node into a random castable type.
     * @param childNode Given context.
     */
    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        XMLDatatype transformedDatatype = XMLDatatype.getRandomDataType();
        transformedDatatypeName = transformedDatatype.getValueHandler().officialTypeName;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.getActualDatatype() != XMLDatatype.NODE;
    }
    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        String returnString = getXPathExpressionCheck(returnConstant, parentNode, calculateString);
        if(returnString != null) return binaryWrap(returnString, parentNode);
        returnString = childList.get(0).getXPathExpression(returnConstant, this, calculateString) + " castable as " +
                transformedDatatypeName;
        cacheXPathExpression(returnString, returnConstant, calculateString);
        return binaryWrap(returnString, parentNode);
    }

    public String binaryWrap(String resultString, LogicTreeNode parentNode) {
        if(parentNode instanceof BinaryOperatorFunctionNode || parentNode instanceof LogicTreeComparisonNode)
            resultString = "(" + resultString + ")";
        return resultString;
    }
}
