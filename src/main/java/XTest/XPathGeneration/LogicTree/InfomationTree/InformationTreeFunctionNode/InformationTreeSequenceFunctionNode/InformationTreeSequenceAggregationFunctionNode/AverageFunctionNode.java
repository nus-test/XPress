package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class AverageFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    AverageFunctionNode() {
        functionExpr = "avg";
    }

    @Override
    public AverageFunctionNode newInstance() {
        return new AverageFunctionNode();
    }
}
