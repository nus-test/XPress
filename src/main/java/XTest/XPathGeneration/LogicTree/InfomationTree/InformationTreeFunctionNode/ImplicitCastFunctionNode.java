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

public class ImplicitCastFunctionNode extends InformationTreeFunctionNode {

    @Override
    public ImplicitCastFunctionNode newInstance() {
        return new ImplicitCastFunctionNode();
    }

    /**
     * Same as fillContentsRandom, implicitly cast the child node into a random castable type.
     * @param childNode Given context.
     */
    @Override
    public void fillContents(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        fillContentsRandom(childNode);
    }

    /**
     * Implicitly cast the child node into a random castable type.
     * @param childNode Given context.
     */
    @Override
    public void fillContentsRandom(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        XMLDatatype originalDatatype = null;
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            originalDatatype = childNode.dataTypeRecorder.subDatatype;
        else
            originalDatatype = childNode.dataTypeRecorder.xmlDatatype;
        XMLDatatype transformedDatatype = XMLDatatype.getRandomCastableIntegratedDatatype(originalDatatype);
        fillContentsSpecificAimedType(childNode, transformedDatatype);
    }

    /**
     * Implicitly cast the child node into a specific castable type.
     * @param childNode Given context.
     */
    public void fillContentsSpecificAimedType(InformationTreeNode childNode, XMLDatatype transformedDatatype) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        XMLDatatype originalDatatype = null;
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            originalDatatype = childNode.dataTypeRecorder.subDatatype;
        else
            originalDatatype = childNode.dataTypeRecorder.xmlDatatype;

        if(!XMLDatatype.checkCastable(mainExecutor, originalDatatype, transformedDatatype)) {
            throw new DebugErrorException("Error: Implicit cast specific transformed datatype is not castable from original datatype.");
        }

        dataTypeRecorder = new XMLDatatypeComplexRecorder(childNode.dataTypeRecorder);
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            context = childNode.context;
            dataTypeRecorder.subDatatype = transformedDatatype;
        }
        else {
            dataTypeRecorder.xmlDatatype = transformedDatatype;
            String originalContext = childNode.context;
            if(originalDatatype == XMLDatatype.NODE) {
                originalContext = "//*[@id=\"" + originalContext + "\"]";
            }
            String XPathExpr = originalContext + " cast as " + transformedDatatype.getValueHandler().officialTypeName;
            context = mainExecutor.executeSingleProcessor(XPathExpr, GlobalSettings.defaultDBName);
        }
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return true;
    }

    public String getCurrentContextFunctionExpr() {
        // TODO: In fact this function should never be called? Not very sure though...
        return ((InformationTreeFunctionNode) childList.get(0)).getCurrentContextFunctionExpr();
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        String returnString = getXPathExpressionCheck(returnConstant);
        if(returnString != null) return returnString;
        returnString = childList.get(0).getXPathExpressionCheck(returnConstant);
        cacheXPathExpression(returnString, returnConstant);
        return returnString;
    }
}
