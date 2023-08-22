package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDuration;
import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class MinutesFromDurationFunctionNode extends InformationTreeFunctionNode {
    public MinutesFromDurationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        functionExpr = "minutes-from-duration";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDuration;
    }

    @Override
    public MinutesFromDurationFunctionNode newInstance() {
        return new MinutesFromDurationFunctionNode();
    }
}
