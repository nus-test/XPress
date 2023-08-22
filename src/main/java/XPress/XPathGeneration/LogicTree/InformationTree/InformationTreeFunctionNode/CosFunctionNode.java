package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.DatatypeControl.PrimitiveDatatype.XMLNumeric;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class CosFunctionNode extends InformationTreeFunctionNode {
    public CosFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDouble.getInstance();
        functionExpr = "math:cos";
    }

    @Override
    public CosFunctionNode newInstance() {
        return new CosFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLNumeric;
    }
}
