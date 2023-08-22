package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDuration;
import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class MonthsFromDurationFunctionNode extends InformationTreeFunctionNode {
    public MonthsFromDurationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        functionExpr = "months-from-duration";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDuration;
    }

    @Override
    public MonthsFromDurationFunctionNode newInstance() {
        return new MonthsFromDurationFunctionNode();
    }
}
