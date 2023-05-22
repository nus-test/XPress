package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class NotFunctionNode extends InformationTreeFunctionNode {
    public NotFunctionNode() {
        functionExpr = "not";
    }

    @Override
    public NotFunctionNode newInstance() {
        return new NotFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.BOOLEAN;
    }

}
