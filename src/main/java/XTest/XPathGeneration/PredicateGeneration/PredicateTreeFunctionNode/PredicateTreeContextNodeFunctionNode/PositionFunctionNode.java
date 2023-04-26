package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeContextNodeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;

public class PositionFunctionNode extends PredicateTreeContextNodeFunctionNode {
    public PositionFunctionNode() {
        XPathExpr = "position";
        datatype = XMLDatatype.INTEGER;
    }
    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        return new PredicateTreeConstantNode(XPathExpr + "()", XMLDatatype.INTEGER);
    }

    @Override
    public String getSubContentXPathGenerator(String XPathPrefix, ContextNode currentNode) {
        return "count(" + XPathPrefix + "[@id=\"" + currentNode.id + "\"]/preceding-sibling::*) + 1";
    }

    @Override
    public PositionFunctionNode newInstance() {
        return new PositionFunctionNode();
    }
}
