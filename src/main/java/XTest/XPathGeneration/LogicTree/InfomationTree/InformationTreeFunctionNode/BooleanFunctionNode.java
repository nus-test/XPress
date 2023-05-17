package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLSimple;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class BooleanFunctionNode extends InformationTreeFunctionNode {
    BooleanFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        functionExpr = "boolean";
    }
    @Override
    public BooleanFunctionNode newInstance() {
        return new BooleanFunctionNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        fillContentsRandom(childNode);
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        childList.add(childNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        if(recorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            return recorder.subDatatype == XMLDatatype.NODE;
        }
        if(recorder.xmlDatatype.getValueHandler() instanceof XMLSimple) return true;
        return false;
    }
}
