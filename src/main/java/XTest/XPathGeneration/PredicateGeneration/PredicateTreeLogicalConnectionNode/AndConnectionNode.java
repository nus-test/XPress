package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode;

public class AndConnectionNode extends PredicateTreeLogicalConnectionNode {

    AndConnectionNode() {
        this.XPathExpr = "and";
    }

    @Override
    public AndConnectionNode newInstance() {
        return new AndConnectionNode();
    }
}
