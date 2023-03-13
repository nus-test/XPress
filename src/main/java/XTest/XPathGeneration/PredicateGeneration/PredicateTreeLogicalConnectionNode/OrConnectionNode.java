package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode;

public class OrConnectionNode extends PredicateTreeLogicalConnectionNode {
    OrConnectionNode() {
        this.XPathExpr = "or";
    }
    @Override
    public OrConnectionNode newInstance() {
        return new OrConnectionNode();
    }
}
