package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

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
