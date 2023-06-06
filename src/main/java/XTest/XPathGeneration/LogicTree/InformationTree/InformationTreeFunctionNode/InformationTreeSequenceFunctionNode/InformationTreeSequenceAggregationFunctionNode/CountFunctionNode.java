package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeContextNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode.InformationTreeDirectContentFunctionNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class CountFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    public CountFunctionNode() {
        functionExpr = "count";
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        boolean result = super.checkContextAcceptability(childNode);
        if(result && GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1) {
            if(childNode instanceof InformationTreeContextNode) return true;
            return childNode instanceof InformationTreeDirectContentFunctionNode;
        }
        return result;
    }



    @Override
    public CountFunctionNode newInstance() {
        return new CountFunctionNode();
    }
}
