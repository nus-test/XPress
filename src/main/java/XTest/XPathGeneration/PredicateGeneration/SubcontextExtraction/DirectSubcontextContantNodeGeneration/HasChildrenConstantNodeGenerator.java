package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction.DirectSubcontextContantNodeGeneration;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class HasChildrenConstantNodeGenerator extends PredicateTreeNodeFromContextGenerator {
    public HasChildrenConstantNodeGenerator() {
        contextFunctionName = "has-children";
    }

    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        return new PredicateTreeConstantNode(contextFunctionName + "()", XMLDatatype.BOOLEAN);
    }
}
