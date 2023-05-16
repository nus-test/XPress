package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class CastableFunctionNode extends InformationTreeFunctionNode {
    String transformedDatatypeName;
    @Override
    public CastableFunctionNode newInstance() {
        return new CastableFunctionNode();
    }

    /**
     * Same as fillContentsRandom, implicitly cast the child node into a random castable type.
     * @param childNode Given context.
     */
    @Override
    public void fillContents(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        XMLDatatype originalDatatype = null;
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            originalDatatype = childNode.dataTypeRecorder.subDatatype;
        else
            originalDatatype = childNode.dataTypeRecorder.xmlDatatype;
        XMLDatatype transformedDatatype = XMLDatatype.getRandomCastableIntegratedDatatype(originalDatatype);
        transformedDatatypeName = transformedDatatype.getValueHandler().officialTypeName;
        childList.add(childNode);
    }

    /**
     * Implicitly cast the child node into a random castable type.
     * @param childNode Given context.
     */
    @Override
    public void fillContentsRandom(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        XMLDatatype transformedDatatype = XMLDatatype.getRandomDataType();
        transformedDatatypeName = transformedDatatype.getValueHandler().officialTypeName;
        childList.add(childNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return true;
    }

    public String getCurrentContextFunctionExpr() {
        return ". castable as " + transformedDatatypeName;
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        String returnString = getXPathExpressionCheck(returnConstant);
        if(returnString != null) return returnString;
        returnString = childList.get(0).getXPathExpression(returnConstant) + " castable as " + transformedDatatypeName;
        cacheXPathExpression(returnString, returnConstant);
        return returnString;
    }
}
