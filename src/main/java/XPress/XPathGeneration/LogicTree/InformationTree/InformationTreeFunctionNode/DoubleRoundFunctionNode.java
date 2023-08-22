package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class DoubleRoundFunctionNode extends InformationTreeFunctionNode {
    public DoubleRoundFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDouble.getInstance();
        functionExpr = "round";
    }

    @Override
    public DoubleRoundFunctionNode newInstance() {
        return new DoubleRoundFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDouble;
    }
}
