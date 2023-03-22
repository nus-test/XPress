package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction.DirectSubcontextContantNodeGeneration;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class LastPredicateTreeNodeGenerator extends PredicateTreeNodeFromContextGenerator {

    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        return new PredicateTreeConstantNode("last()", XMLDatatype.INTEGER);
    }

    @Override
    public String getSubContentXPathGenerator(String XPathPrefix, ContextNode currentNode) {
        return "count(" + XPathPrefix + ")";
    }
}
