package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.PrimitiveDatatype.XMLDurationHandler;
import XPress.PrimitiveDatatype.XMLSimple;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class BooleanFunctionNode extends InformationTreeFunctionNode {
    public BooleanFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        functionExpr = "boolean";
    }
    @Override
    public BooleanFunctionNode newInstance() {
        return new BooleanFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            return childNode.datatypeRecorder.subDatatype == XMLDatatype.NODE;
        }
        if(childNode.datatypeRecorder.xmlDatatype.getValueHandler() instanceof XMLDurationHandler) return false;
        if(childNode.datatypeRecorder.xmlDatatype.getValueHandler() instanceof XMLSimple) return true;
        return false;
    }
}
