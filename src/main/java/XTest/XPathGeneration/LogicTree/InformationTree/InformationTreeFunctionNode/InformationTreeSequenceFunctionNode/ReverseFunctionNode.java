package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

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
