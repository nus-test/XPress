package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction.DirectSubcontextContantNodeGeneration;

import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class TextPredicateTreeNodeGenerator extends PredicateTreeNodeFromContextGenerator {
    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        PredicateTreeConstantNode node = new PredicateTreeConstantNode(currentNode, "text()");
        return node;
    }
}
