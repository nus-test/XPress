package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class DoubleFloorFunctionNode extends InformationTreeFunctionNode {

    public DoubleFloorFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        functionExpr = "floor";
    }

    @Override
    public DoubleFloorFunctionNode newInstance() {
        return new DoubleFloorFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
}
