package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

@FunctionV1
public class AndFunctionNode extends BinaryLogicOperatorFunctionNode {
    public AndFunctionNode() {
        functionExpr = "and";
    }

    @Override
    public AndFunctionNode newInstance() {
        return new AndFunctionNode();
    }
}
