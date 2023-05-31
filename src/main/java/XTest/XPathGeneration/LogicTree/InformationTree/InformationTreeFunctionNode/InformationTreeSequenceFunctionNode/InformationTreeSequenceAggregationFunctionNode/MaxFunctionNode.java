package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLNumeric;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class MaxFunctionNode extends InformationTreeSequenceNumericalAggregationFunctionNode {
    public MaxFunctionNode() {
        functionExpr = "max";
    }


    @Override
    public MaxFunctionNode newInstance() {
        return new MaxFunctionNode();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            datatypeRecorder.xmlDatatype = childNode.datatypeRecorder.subDatatype;
        else datatypeRecorder.xmlDatatype = childNode.datatypeRecorder.xmlDatatype;
    }
}
