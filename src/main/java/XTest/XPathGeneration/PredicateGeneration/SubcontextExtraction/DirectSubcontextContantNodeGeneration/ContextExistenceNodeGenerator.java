package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction.DirectSubcontextContantNodeGeneration;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;

public class ContextExistenceNodeGenerator extends PredicateTreeNodeFromContextGenerator {
    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        PredicateTreeConstantNode node = new PredicateTreeConstantNode(currentNode,"");
        return node;
    }
}
