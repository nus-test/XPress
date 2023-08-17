package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class UpperCaseFunctionNode extends InformationTreeFunctionNode {
    public UpperCaseFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "upper-case";
    }
    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }
    @Override
    public UpperCaseFunctionNode newInstance() {
        return new UpperCaseFunctionNode();
    }
}
