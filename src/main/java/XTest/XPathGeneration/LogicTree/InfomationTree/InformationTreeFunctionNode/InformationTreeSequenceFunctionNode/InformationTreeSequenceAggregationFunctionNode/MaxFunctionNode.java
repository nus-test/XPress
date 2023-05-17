package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLNumeric;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class MaxFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    public MaxFunctionNode() {
        functionExpr = "max";
    }


    @Override
    public MaxFunctionNode newInstance() {
        return new MaxFunctionNode();
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            datatypeRecorder.xmlDatatype = childNode.datatypeRecorder.subDatatype;
        else datatypeRecorder.xmlDatatype = childNode.datatypeRecorder.xmlDatatype;
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
