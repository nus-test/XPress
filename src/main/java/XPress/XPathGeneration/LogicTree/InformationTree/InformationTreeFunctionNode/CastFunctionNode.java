package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.DatatypeControl.PrimitiveDatatype.XMLBoolean;
import XPress.DatatypeControl.PrimitiveDatatype.XMLNode;
import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.GlobalSettings;
import XPress.DatatypeControl.XMLDatatypeComplexRecorder;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XPress.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class CastFunctionNode extends BinaryOperatorFunctionNode {
    XMLDatatype internalDatatype;
    XMLDatatype internalSupplementaryDatatype;
    XMLDatatype originalDatatype;
    XMLDatatype castedDatatype;

    @Override
    public CastFunctionNode newInstance() {
        return new CastFunctionNode();
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
        if(internalDatatype == null) {
            XMLDatatype originalDatatype = null;
            if (childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence)
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
        if(transformedRecorder.subDatatype instanceof XMLNode || transformedRecorder.xmlDatatype instanceof XMLNode)
            throw new DebugErrorException("Should not cast any data into nodes");
        if(!(childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence)) {
            if(!(transformedRecorder.xmlDatatype instanceof XMLSequence)) {
                internalDatatype = transformedRecorder.xmlDatatype;
                fillContentsRandom(childNode);
            }
            else {
                throw new DebugErrorException("Should not need to cast single node into sequence through cast");
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
    protected void fillContentParametersSpecificAimedType(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        if(internalDatatype instanceof XMLNode)
            throw new DebugErrorException("Should not cast any data into nodes");
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence)
            originalDatatype = childNode.datatypeRecorder.subDatatype;
        else
            originalDatatype = childNode.datatypeRecorder.xmlDatatype;

        if(!XMLDatatype.checkCastable(contextInfo.mainExecutor, originalDatatype, internalDatatype)) {
            throw new DebugErrorException("Error: Cast specific transformed datatype is not castable from original datatype.");
        }

        datatypeRecorder = new XMLDatatypeComplexRecorder(childNode.datatypeRecorder);
        castedDatatype = internalDatatype;
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence) {
            context = childNode.context;
            datatypeRecorder.subDatatype = internalDatatype;
        }
        else {
            datatypeRecorder.xmlDatatype = internalDatatype;
            String originalContext = childNode.getContext().context;
            if(originalDatatype instanceof XMLNode) {
                originalContext = "//*[@id=\"" + originalContext + "\"]";
            }
            else {
                originalContext = XMLDatatype.wrapExpression(originalContext, originalDatatype);
            }
            String XPathExpr;
            if(!(internalDatatype instanceof XMLBoolean))
                XPathExpr = originalContext + " cast as " + internalDatatype.officialTypeName;
            else XPathExpr = "boolean(" + originalContext + ")";
            getContext().context = contextInfo.mainExecutor.executeSingleProcessor(XPathExpr, GlobalSettings.defaultDBName);
        }
        castedDatatype = internalDatatype;
        internalDatatype = null;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return !(childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence);
    }

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        String returnString = getXPathExpressionCheck(returnConstant, parentNode, calculateString);
        if(returnString != null) return returnString;
        if(!(childList.get(0).datatypeRecorder.xmlDatatype instanceof XMLSequence) && (!(originalDatatype instanceof XMLNode) || calculateString)) {
            returnString = binaryWrap(childList.get(0).getXPathExpression(returnConstant, this, calculateString)
                    + " cast as " + castedDatatype.officialTypeName, parentNode);
        }
        else returnString = childList.get(0).getXPathExpression(returnConstant, parentNode, calculateString);
        cacheXPathExpression(returnString, returnConstant, calculateString);
        return returnString;
    }
}
