package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class MinFunctionNode extends InformationTreeSequenceNumericalAggregationFunctionNode {
    public MinFunctionNode() {
        functionExpr = "min";
    }

    @Override
    public MinFunctionNode newInstance() {
        return new MinFunctionNode();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            datatypeRecorder.xmlDatatype = childNode.datatypeRecorder.subDatatype;
        else datatypeRecorder.xmlDatatype = childNode.datatypeRecorder.xmlDatatype;
    }
}
