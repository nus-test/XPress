package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class DoubleRoundHalfToEvenFunctionNode extends InformationTreeFunctionNode {
    public DoubleRoundHalfToEvenFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDouble.getInstance();
        functionExpr = "round-half-to-even";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDouble;
    }
    @Override
    public DoubleRoundHalfToEvenFunctionNode newInstance() {
        return new DoubleRoundHalfToEvenFunctionNode();
    }
}
