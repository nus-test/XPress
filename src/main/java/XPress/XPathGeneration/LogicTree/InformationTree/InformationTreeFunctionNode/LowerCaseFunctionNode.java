package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class LowerCaseFunctionNode extends InformationTreeFunctionNode {
    public LowerCaseFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLString.getInstance();
        functionExpr = "lower-case";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
    }

    @Override
    public LowerCaseFunctionNode newInstance() {
        return new LowerCaseFunctionNode();
    }

}
