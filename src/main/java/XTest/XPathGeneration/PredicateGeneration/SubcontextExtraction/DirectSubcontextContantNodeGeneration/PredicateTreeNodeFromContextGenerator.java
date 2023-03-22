package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction.DirectSubcontextContantNodeGeneration;

import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public abstract class PredicateTreeNodeFromContextGenerator {
    String contextFunctionName;

    public abstract PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode);

    public String getSubContentXPathGenerator(String XPathPrefix, ContextNode currentNode) {
        return XPathPrefix + "[@id=\"" + currentNode.id + "\"]/" + contextFunctionName + "()";
    }
}

