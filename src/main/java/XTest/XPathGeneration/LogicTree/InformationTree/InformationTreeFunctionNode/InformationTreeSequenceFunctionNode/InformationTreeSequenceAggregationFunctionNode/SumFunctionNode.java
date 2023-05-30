package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLNumeric;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class SumFunctionNode extends InformationTreeSequenceNumericalAggregationFunctionNode {
    public SumFunctionNode() {
        functionExpr = "sum";
    }

    @Override
    public SumFunctionNode newInstance() {
        return new SumFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        XMLDatatype datatype;
        if(recorder.xmlDatatype == XMLDatatype.SEQUENCE)
            datatype = recorder.subDatatype;
        else datatype = recorder.xmlDatatype;
        return datatype.getValueHandler() instanceof XMLNumeric;
    }
}
