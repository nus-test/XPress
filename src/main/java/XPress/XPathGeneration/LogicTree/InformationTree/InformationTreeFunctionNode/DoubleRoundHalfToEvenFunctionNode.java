package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

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
