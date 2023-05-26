package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public class DaysFromDurationFunctionNode extends InformationTreeFunctionNode {
    public DaysFromDurationFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "days-from-duration";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DURATION;
    }

    @Override
    public DaysFromDurationFunctionNode newInstance() {
        return new DaysFromDurationFunctionNode();
    }

}
