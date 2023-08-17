package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;

@FunctionV3
public class ReverseFunctionNode extends InformationTreeSequenceFunctionNode {
    public ReverseFunctionNode() {
        functionExpr = "reverse";
    }
    @Override
    public ReverseFunctionNode newInstance() {
        return new ReverseFunctionNode();
    }
}
