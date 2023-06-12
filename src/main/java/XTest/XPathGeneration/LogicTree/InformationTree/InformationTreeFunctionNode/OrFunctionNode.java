package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

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
