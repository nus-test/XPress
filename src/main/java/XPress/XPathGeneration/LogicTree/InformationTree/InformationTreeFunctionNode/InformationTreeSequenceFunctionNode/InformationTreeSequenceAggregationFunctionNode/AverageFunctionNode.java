package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class AverageFunctionNode extends InformationTreeSequenceNumericalAggregationFunctionNode {
    public AverageFunctionNode() {
        functionExpr = "avg";
        datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
    }

    @Override
    public AverageFunctionNode newInstance() {
        return new AverageFunctionNode();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
    }
}
