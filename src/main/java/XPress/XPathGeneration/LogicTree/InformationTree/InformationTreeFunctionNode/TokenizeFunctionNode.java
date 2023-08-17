package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.GlobalSettings;
import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class TokenizeFunctionNode extends InformationTreeFunctionNode {
    public TokenizeFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
        datatypeRecorder.subDatatype = XMLDatatype.STRING;
        functionExpr = "tokenize";
    }
    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        boolean flag = childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
        if(flag && GlobalSettings.starNodeSelection)
            if(childNode.context.context.length() == 0) return false;
        return flag;
    }
    @Override
    public TokenizeFunctionNode newInstance() {
        return new TokenizeFunctionNode();
    }
}
