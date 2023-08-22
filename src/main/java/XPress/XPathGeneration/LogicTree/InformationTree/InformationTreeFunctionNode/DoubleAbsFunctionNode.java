package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class DoubleAbsFunctionNode extends InformationTreeFunctionNode {
    public DoubleAbsFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDouble.getInstance();
        functionExpr = "abs";
    }

    @Override
    public DoubleAbsFunctionNode newInstance() {
        return new DoubleAbsFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDouble;
    }
}
