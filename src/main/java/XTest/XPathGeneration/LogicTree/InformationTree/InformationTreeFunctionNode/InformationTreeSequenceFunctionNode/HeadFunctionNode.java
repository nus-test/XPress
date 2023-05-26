package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

public class HeadFunctionNode extends InformationTreeSequenceFunctionNode {
    public HeadFunctionNode() {
        functionExpr = "head";
    }

    @Override
    public HeadFunctionNode newInstance() {
        return new HeadFunctionNode();
    }
}
