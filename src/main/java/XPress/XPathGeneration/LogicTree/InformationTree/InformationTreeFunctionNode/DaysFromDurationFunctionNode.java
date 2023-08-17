package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class DaysFromDurationFunctionNode extends InformationTreeFunctionNode {
    public DaysFromDurationFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "days-from-duration";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.DURATION;
    }

    @Override
    public DaysFromDurationFunctionNode newInstance() {
        return new DaysFromDurationFunctionNode();
    }

}
