package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLNumeric;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

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
