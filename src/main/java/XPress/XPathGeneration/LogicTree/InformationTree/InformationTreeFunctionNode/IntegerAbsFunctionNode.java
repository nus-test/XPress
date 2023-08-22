package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class IntegerAbsFunctionNode extends InformationTreeFunctionNode {
    public IntegerAbsFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        functionExpr = "abs";
    }

    @Override
    public IntegerAbsFunctionNode newInstance() {
        return new IntegerAbsFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLInteger;
    }
}
