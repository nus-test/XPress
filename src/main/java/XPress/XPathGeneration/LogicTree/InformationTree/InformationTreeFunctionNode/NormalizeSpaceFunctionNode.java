package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class NormalizeSpaceFunctionNode extends InformationTreeFunctionNode {
    public NormalizeSpaceFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLString.getInstance();
        functionExpr = "normalize-space";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
    }

    @Override
    public NormalizeSpaceFunctionNode newInstance() {
        return new NormalizeSpaceFunctionNode();
    }
}
