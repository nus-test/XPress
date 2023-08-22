package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class DoubleCeilingFunctionNode extends InformationTreeFunctionNode {
    public DoubleCeilingFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDouble.getInstance();
        functionExpr = "ceiling";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDouble;
    }
    @Override
    public DoubleCeilingFunctionNode newInstance() {
        return new DoubleCeilingFunctionNode();
    }
}
