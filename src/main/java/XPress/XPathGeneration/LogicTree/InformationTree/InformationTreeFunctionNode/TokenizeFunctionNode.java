package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.GlobalSettings;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class TokenizeFunctionNode extends InformationTreeFunctionNode {
    public TokenizeFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLSequence.getInstance();
        datatypeRecorder.subDatatype = XMLString.getInstance();
        functionExpr = "tokenize";
    }
    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        boolean flag = childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
        if(flag && GlobalSettings.starNodeSelection)
            if(childNode.context.context.length() == 0) return false;
        return flag;
    }
    @Override
    public TokenizeFunctionNode newInstance() {
        return new TokenizeFunctionNode();
    }
}
