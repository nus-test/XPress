package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class DoubleCeilingFunctionNode extends InformationTreeFunctionNode {
    public DoubleCeilingFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        functionExpr = "ceiling";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
    @Override
    public DoubleCeilingFunctionNode newInstance() {
        return new DoubleCeilingFunctionNode();
    }
}
