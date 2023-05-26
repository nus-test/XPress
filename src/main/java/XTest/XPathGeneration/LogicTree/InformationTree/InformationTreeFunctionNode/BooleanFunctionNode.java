package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLDurationHandler;
import XTest.PrimitiveDatatype.XMLSimple;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public class BooleanFunctionNode extends InformationTreeFunctionNode {
    public BooleanFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        functionExpr = "boolean";
    }
    @Override
    public BooleanFunctionNode newInstance() {
        return new BooleanFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        if(recorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            return recorder.subDatatype == XMLDatatype.NODE;
        }
        if(recorder.xmlDatatype.getValueHandler() instanceof XMLDurationHandler) return false;
        if(recorder.xmlDatatype.getValueHandler() instanceof XMLSimple) return true;
        return false;
    }
}
