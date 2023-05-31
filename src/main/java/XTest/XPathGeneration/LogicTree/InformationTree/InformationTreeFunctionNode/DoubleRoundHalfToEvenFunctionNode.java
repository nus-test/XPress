package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class DoubleRoundHalfToEvenFunctionNode extends InformationTreeFunctionNode {
    public DoubleRoundHalfToEvenFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        functionExpr = "round-half-to-even";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
    @Override
    public DoubleRoundHalfToEvenFunctionNode newInstance() {
        return new DoubleRoundHalfToEvenFunctionNode();
    }
}
