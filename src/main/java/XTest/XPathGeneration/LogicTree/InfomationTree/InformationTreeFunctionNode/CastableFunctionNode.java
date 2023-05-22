package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeComparisonNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class CastableFunctionNode extends BinaryOperatorFunctionNode {
    String transformedDatatypeName;
    public CastableFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        priorityLevel = 1;
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
    public void fillContentParameters(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        fillContentParametersRandom(childNode);
    }

    /**
     * Implicitly cast the child node into a random castable type.
     * @param childNode Given context.
     */
    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        XMLDatatype transformedDatatype = XMLDatatype.getRandomDataType();
        transformedDatatypeName = transformedDatatype.getValueHandler().officialTypeName;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return true;
    }

    public String getCurrentContextFunctionExpr() {
        return ". castable as " + transformedDatatypeName;
    }

    @Override
    public String getCurrentLevelCalculationString() {
        return "//*[@id=\"" + childList.get(0).context + "\"] castable as " + transformedDatatypeName;
    }

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode) {
        String returnString = getXPathExpressionCheck(returnConstant);
        if(returnString != null) return returnString;
        returnString = childList.get(0).getXPathExpression(returnConstant, this) + " castable as " + transformedDatatypeName;
        if(parentNode instanceof BinaryOperatorFunctionNode || parentNode instanceof LogicTreeComparisonNode)
            returnString = "(" + returnString + ")";
        cacheXPathExpression(returnString, returnConstant);
        return returnString;
    }
}
