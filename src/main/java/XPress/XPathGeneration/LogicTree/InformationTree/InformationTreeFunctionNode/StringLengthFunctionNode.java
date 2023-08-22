package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class StringLengthFunctionNode extends InformationTreeFunctionNode {
    public StringLengthFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        functionExpr = "string-length";
    }

    @Override
    public StringLengthFunctionNode newInstance() {
        return new StringLengthFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
    }
}
