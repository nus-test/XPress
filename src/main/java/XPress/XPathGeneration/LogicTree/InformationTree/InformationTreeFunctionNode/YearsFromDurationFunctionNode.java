package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDuration;
import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class YearsFromDurationFunctionNode extends InformationTreeFunctionNode {
    public YearsFromDurationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        functionExpr = "years-from-duration";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDuration;
    }

    @Override
    public YearsFromDurationFunctionNode newInstance() {
        return new YearsFromDurationFunctionNode();
    }
}
