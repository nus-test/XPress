package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

public class SortFunctionNode extends InformationTreeSequenceFunctionNode {

    public SortFunctionNode() {
        functionExpr = "sort";
    }

    @Override
    public SortFunctionNode newInstance() {
        return new SortFunctionNode();
    }
}
