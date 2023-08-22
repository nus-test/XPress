package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDuration;
import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class SecondsFromDurationFunctionNode extends InformationTreeFunctionNode {
    public SecondsFromDurationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        functionExpr = "seconds-from-duration";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDuration;
    }

    @Override
    public SecondsFromDurationFunctionNode newInstance() {
        return new SecondsFromDurationFunctionNode();
    }
}
