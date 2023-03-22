package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction.DirectSubcontextContantNodeGeneration;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class PositionPredicateTreeNodeGenerator extends PredicateTreeNodeFromContextGenerator {
    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        return new PredicateTreeConstantNode("position()", XMLDatatype.INTEGER);
    }

    @Override
    public String getSubContentXPathGenerator(String XPathPrefix, ContextNode currentNode) {
        return "count(" + XPathPrefix + "[@id=\"" + currentNode.id + "\"]/preceding-sibling::*) + 1";
    }
}
