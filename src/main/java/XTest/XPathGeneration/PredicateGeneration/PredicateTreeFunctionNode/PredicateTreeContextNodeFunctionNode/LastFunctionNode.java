package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeContextNodeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;

public class LastFunctionNode extends PredicateTreeContextNodeFunctionNode {
    public LastFunctionNode() {
        XPathExpr = "last";
        datatype = XMLDatatype.INTEGER;
    }

    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        return new PredicateTreeConstantNode(XPathExpr + "()", XMLDatatype.INTEGER);
    }

    @Override
    public String getSubContentXPathGenerator(String XPathPrefix, ContextNode currentNode) {
        return "count(" + XPathPrefix + ")";
    }

    @Override
    public LastFunctionNode newInstance() {
        return new LastFunctionNode();
    }
}
