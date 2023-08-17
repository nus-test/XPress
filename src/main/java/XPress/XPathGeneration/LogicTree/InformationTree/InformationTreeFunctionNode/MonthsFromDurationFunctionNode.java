package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class MonthsFromDurationFunctionNode extends InformationTreeFunctionNode {
    public MonthsFromDurationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "months-from-duration";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.DURATION;
    }

    @Override
    public MonthsFromDurationFunctionNode newInstance() {
        return new MonthsFromDurationFunctionNode();
    }
}
