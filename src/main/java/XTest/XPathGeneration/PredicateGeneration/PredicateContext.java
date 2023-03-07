package XTest.XPathGeneration.PredicateGeneration;

import XTest.XMLGeneration.ContextNode;

import java.util.List;

public class PredicateContext {
    public String predicate;
    public String XPathPrefix;
    public List<ContextNode> executionResult;

    public PredicateContext(String predicate, String XPathPrefix, List<ContextNode> executionResult) {
        this.predicate = predicate;
        this.XPathPrefix = XPathPrefix;
        this.executionResult = executionResult;
    }
}
