package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class DoubleFloorFunctionNode extends InformationTreeFunctionNode {

    public DoubleFloorFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDouble.getInstance();
        functionExpr = "floor";
    }

    @Override
    public DoubleFloorFunctionNode newInstance() {
        return new DoubleFloorFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDouble;
    }
}
