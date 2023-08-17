package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

@FunctionV1
public class OrFunctionNode extends BinaryLogicOperatorFunctionNode {
    public OrFunctionNode() {
        functionExpr = "or";
    }

    @Override
    public OrFunctionNode newInstance() {
        return new OrFunctionNode();
    }
}
