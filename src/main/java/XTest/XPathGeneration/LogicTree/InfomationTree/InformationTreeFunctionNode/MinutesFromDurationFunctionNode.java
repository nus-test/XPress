package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class MinutesFromDurationFunctionNode extends InformationTreeFunctionNode {
    public MinutesFromDurationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "minutes-from-duration";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DURATION;
    }

    @Override
    public MinutesFromDurationFunctionNode newInstance() {
        return new MinutesFromDurationFunctionNode();
    }
}
