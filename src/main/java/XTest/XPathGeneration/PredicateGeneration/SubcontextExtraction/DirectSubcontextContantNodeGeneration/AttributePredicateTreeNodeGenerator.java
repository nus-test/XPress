package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction.DirectSubcontextContantNodeGeneration;

import XTest.GlobalRandom;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class AttributePredicateTreeNodeGenerator extends PredicateTreeNodeFromContextGenerator {
    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        return new PredicateTreeConstantNode(GlobalRandom.getInstance().getRandomFromList(currentNode.attributeList));
    }
}
