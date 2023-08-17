package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class SecondsFromDurationFunctionNode extends InformationTreeFunctionNode {
    public SecondsFromDurationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "seconds-from-duration";
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.DURATION;
    }

    @Override
    public SecondsFromDurationFunctionNode newInstance() {
        return new SecondsFromDurationFunctionNode();
    }
}
