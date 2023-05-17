package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class DoubleFloorFunctionNode extends InformationTreeFunctionNode {

    DoubleFloorFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        functionExpr = "floor";
    }

    @Override
    public DoubleFloorFunctionNode newInstance() {
        return new DoubleFloorFunctionNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        fillContentsRandom(childNode);
    }
    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
}
