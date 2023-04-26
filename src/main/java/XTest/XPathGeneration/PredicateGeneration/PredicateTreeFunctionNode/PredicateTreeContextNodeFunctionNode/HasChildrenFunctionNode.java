package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeContextNodeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;

public class HasChildrenFunctionNode extends PredicateTreeContextNodeFunctionNode {
    public HasChildrenFunctionNode() {
        XPathExpr = "has-children";
        datatype = XMLDatatype.BOOLEAN;
    }

    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        return new PredicateTreeConstantNode(XPathExpr + "()", XMLDatatype.BOOLEAN);
    }

    @Override
    public HasChildrenFunctionNode newInstance() {
        return new HasChildrenFunctionNode();
    }
}
