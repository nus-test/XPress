package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLBoolean;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class NotFunctionNode extends InformationTreeFunctionNode {
    public NotFunctionNode() {
        functionExpr = "not";
        datatypeRecorder.xmlDatatype = XMLBoolean.getInstance();
    }

    @Override
    public NotFunctionNode newInstance() {
        return new NotFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLBoolean;
    }

}
