package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.PrimitiveDatatype.XMLNumeric;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class SinFunctionNode extends InformationTreeFunctionNode {
    public SinFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        functionExpr = "math:sin";
    }

    @Override
    public SinFunctionNode newInstance() {
        return new SinFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype.getValueHandler() instanceof XMLNumeric;
    }
}
