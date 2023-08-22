package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class AverageFunctionNode extends InformationTreeSequenceNumericalAggregationFunctionNode {
    public AverageFunctionNode() {
        functionExpr = "avg";
        datatypeRecorder.xmlDatatype = XMLDouble.getInstance();
    }

    @Override
    public AverageFunctionNode newInstance() {
        return new AverageFunctionNode();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
    }
}
