package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

public class ReverseFunctionNode extends InformationTreeSequenceFunctionNode {
    public ReverseFunctionNode() {
        functionExpr = "reverse";
    }
    @Override
    public ReverseFunctionNode newInstance() {
        return new ReverseFunctionNode();
    }
}
