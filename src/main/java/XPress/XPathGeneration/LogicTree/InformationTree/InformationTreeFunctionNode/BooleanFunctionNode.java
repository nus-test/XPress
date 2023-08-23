package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.*;
import XPress.DatatypeControl.ValueHandler.XMLDurationHandler;
import XPress.DatatypeControl.XMLSimple;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class BooleanFunctionNode extends InformationTreeFunctionNode {
    public BooleanFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLBoolean.getInstance();
        functionExpr = "boolean";
    }
    @Override
    public BooleanFunctionNode newInstance() {
        return new BooleanFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence) {
            return childNode.datatypeRecorder.subDatatype instanceof XMLNode;
        }
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLQName) return false;
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLDuration) return false;
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLSimple) return true;
        return false;
    }
}
