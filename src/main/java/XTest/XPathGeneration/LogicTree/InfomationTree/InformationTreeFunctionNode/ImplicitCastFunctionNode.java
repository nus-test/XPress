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
    XMLDatatype internalDatatype;

    @Override
    public ImplicitCastFunctionNode newInstance() {
        return new ImplicitCastFunctionNode();
    }

    /**
     * Same as fillContentsRandom, implicitly cast the child node into a random castable type.
     * @param childNode Given context.
     */
    @Override
    protected void fillContentParameters(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        fillContentParametersRandom(childNode);
    }

    /**
     * Implicitly cast the child node into a random castable type.
     * @param childNode Given context.
     */
    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        if(internalDatatype == null) {
            XMLDatatype originalDatatype = null;
            if (childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
                originalDatatype = childNode.datatypeRecorder.subDatatype;
            else
                originalDatatype = childNode.datatypeRecorder.xmlDatatype;
            XMLDatatype transformedDatatype = XMLDatatype.getRandomCastableIntegratedDatatype(originalDatatype);
            internalDatatype = transformedDatatype;
        }
        fillContentParametersSpecificAimedType(childNode);
    }

    /**
     * Implicitly cast the child node into a specific castable type.
     * @param childNode Given context.
     */
    public void fillContentsSpecificAimedType(InformationTreeNode childNode, XMLDatatype transformedDatatype) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        internalDatatype = transformedDatatype;
        fillContentsRandom(childNode);
    }

    /**
     * Implicitly cast the child node into a specific castable type recorder.
     * @param childNode Given context.
     */
    public void fillContentsSpecificAimedType(InformationTreeNode childNode, XMLDatatypeComplexRecorder transformedRecorder) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        if(transformedRecorder.subDatatype == XMLDatatype.NODE || transformedRecorder.xmlDatatype == XMLDatatype.NODE)
            throw new DebugErrorException("Should not cast any data into nodes");
        if(childNode.datatypeRecorder.xmlDatatype != XMLDatatype.SEQUENCE) {
            if(transformedRecorder.xmlDatatype != XMLDatatype.SEQUENCE) {
                internalDatatype = transformedRecorder.xmlDatatype;
                fillContentsRandom(childNode);
            }
            else {
                datatypeRecorder = transformedRecorder;
                context = "1";
            }
        }
        else {
            internalDatatype = transformedRecorder.subDatatype;
            fillContentsRandom(childNode);
        }
    }

    /**
     * Implicitly cast the child node into a specific castable type.
     * @param childNode Given context.
     */
    protected void fillContentParametersSpecificAimedType(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        if(internalDatatype == XMLDatatype.NODE)
            throw new DebugErrorException("Should not cast any data into nodes");
        XMLDatatype originalDatatype = null;
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            originalDatatype = childNode.datatypeRecorder.subDatatype;
        else
            originalDatatype = childNode.datatypeRecorder.xmlDatatype;

        if(!XMLDatatype.checkCastable(contextInfo.mainExecutor, originalDatatype, internalDatatype)) {
            throw new DebugErrorException("Error: Implicit cast specific transformed datatype is not castable from original datatype.");
        }

        datatypeRecorder = new XMLDatatypeComplexRecorder(childNode.datatypeRecorder);
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            context = childNode.context;
            datatypeRecorder.subDatatype = internalDatatype;
        }
        else {
            datatypeRecorder.xmlDatatype = internalDatatype;
            String originalContext = childNode.context;
            if(originalDatatype == XMLDatatype.NODE) {
                originalContext = "//*[@id=\"" + originalContext + "\"]";
            }
            String XPathExpr;
            if(internalDatatype != XMLDatatype.BOOLEAN)
                XPathExpr = originalContext + " cast as " + internalDatatype.getValueHandler().officialTypeName;
            else XPathExpr = "boolean(" + originalContext + ")";
            context = contextInfo.mainExecutor.executeSingleProcessor(XPathExpr, GlobalSettings.defaultDBName);
        }
        internalDatatype = null;
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
