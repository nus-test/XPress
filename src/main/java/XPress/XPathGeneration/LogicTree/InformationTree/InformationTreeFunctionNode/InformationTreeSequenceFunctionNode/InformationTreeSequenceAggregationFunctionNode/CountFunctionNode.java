package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.GlobalSettings;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeContextNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode.InformationTreeDirectContentFunctionNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class CountFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    public CountFunctionNode() {
        functionExpr = "count";
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
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
