package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class NormalizeSpaceFunctionNode extends InformationTreeFunctionNode {
    public NormalizeSpaceFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "normalize-space";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }

    @Override
    public NormalizeSpaceFunctionNode newInstance() {
        return new NormalizeSpaceFunctionNode();
    }
}
