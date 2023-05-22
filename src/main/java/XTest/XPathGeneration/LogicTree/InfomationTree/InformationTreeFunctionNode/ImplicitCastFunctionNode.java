package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
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
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            originalDatatype = childNode.datatypeRecorder.subDatatype;
        else
            originalDatatype = childNode.datatypeRecorder.xmlDatatype;
        XMLDatatype transformedDatatype = XMLDatatype.getRandomCastableIntegratedDatatype(originalDatatype);
        fillContentsSpecificAimedType(childNode, transformedDatatype);
    }

    /**
     * Implicitly cast the child node into a specific castable type.
     * @param childNode Given context.
     */
    public void fillContentsSpecificAimedType(InformationTreeNode childNode, XMLDatatype transformedDatatype) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        if(transformedDatatype == XMLDatatype.NODE)
            throw new DebugErrorException("Should not cast any data into nodes");
        XMLDatatype originalDatatype = null;
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            originalDatatype = childNode.datatypeRecorder.subDatatype;
        else
            originalDatatype = childNode.datatypeRecorder.xmlDatatype;

        if(!XMLDatatype.checkCastable(contextInfo.mainExecutor, originalDatatype, transformedDatatype)) {
            throw new DebugErrorException("Error: Implicit cast specific transformed datatype is not castable from original datatype.");
        }

        datatypeRecorder = new XMLDatatypeComplexRecorder(childNode.datatypeRecorder);
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            context = childNode.context;
            datatypeRecorder.subDatatype = transformedDatatype;
        }
        else {
            datatypeRecorder.xmlDatatype = transformedDatatype;
            String originalContext = childNode.context;
            if(originalDatatype == XMLDatatype.NODE) {
                originalContext = "//*[@id=\"" + originalContext + "\"]";
            }
            String XPathExpr;
            if(transformedDatatype != XMLDatatype.BOOLEAN)
                XPathExpr = originalContext + " cast as " + transformedDatatype.getValueHandler().officialTypeName;
            else XPathExpr = "boolean(" + originalContext + ")";
            context = contextInfo.mainExecutor.executeSingleProcessor(XPathExpr, GlobalSettings.defaultDBName);
        }
        childList.add(childNode);
        inheritContextChildInfo(childNode);
    }

    /**
     * Implicitly cast the child node into a specific castable type recorder.
     * @param childNode Given context.
     */
    public void fillContentsSpecificAimedType(InformationTreeNode childNode, XMLDatatypeComplexRecorder transformedRecorder) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        if(transformedRecorder.subDatatype == XMLDatatype.NODE || transformedRecorder.xmlDatatype == XMLDatatype.NODE)
            throw new DebugErrorException("Should not cast any data into nodes");
        if(childNode.datatypeRecorder.xmlDatatype != XMLDatatype.SEQUENCE) {
            if(transformedRecorder.xmlDatatype != XMLDatatype.SEQUENCE)
                fillContentsSpecificAimedType(childNode, transformedRecorder.xmlDatatype);
            else {
                datatypeRecorder = transformedRecorder;
                context = "1";
            }
        }
        else fillContentsSpecificAimedType(childNode, transformedRecorder.subDatatype);
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
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode) {
        String returnString = getXPathExpressionCheck(returnConstant);
        if(returnString != null) return returnString;
        returnString = childList.get(0).getXPathExpression(returnConstant, parentNode);
        cacheXPathExpression(returnString, returnConstant);
        return returnString;
    }
}
