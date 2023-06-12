package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

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
