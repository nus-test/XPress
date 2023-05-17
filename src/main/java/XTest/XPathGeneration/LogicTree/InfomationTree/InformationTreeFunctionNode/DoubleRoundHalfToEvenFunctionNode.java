package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class DoubleRoundHalfToEvenFunctionNode extends InformationTreeFunctionNode {
    DoubleRoundHalfToEvenFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        functionExpr = "round-half-to-even";
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
    @Override
    public DoubleRoundHalfToEvenFunctionNode newInstance() {
        return new DoubleRoundHalfToEvenFunctionNode();
    }
}