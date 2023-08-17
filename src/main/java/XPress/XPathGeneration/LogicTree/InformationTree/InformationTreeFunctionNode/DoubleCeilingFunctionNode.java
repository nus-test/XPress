package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class DoubleCeilingFunctionNode extends InformationTreeFunctionNode {
    public DoubleCeilingFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        functionExpr = "ceiling";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
    @Override
    public DoubleCeilingFunctionNode newInstance() {
        return new DoubleCeilingFunctionNode();
    }
}
