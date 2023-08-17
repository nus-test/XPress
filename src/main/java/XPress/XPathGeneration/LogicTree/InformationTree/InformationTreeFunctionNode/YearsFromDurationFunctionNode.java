package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class YearsFromDurationFunctionNode extends InformationTreeFunctionNode {
    public YearsFromDurationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "years-from-duration";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.DURATION;
    }

    @Override
    public YearsFromDurationFunctionNode newInstance() {
        return new YearsFromDurationFunctionNode();
    }
}
