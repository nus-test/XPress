package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public class DoubleAbsFunctionNode extends InformationTreeFunctionNode {
    public DoubleAbsFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        functionExpr = "abs";
    }

    @Override
    public DoubleAbsFunctionNode newInstance() {
        return new DoubleAbsFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
}
