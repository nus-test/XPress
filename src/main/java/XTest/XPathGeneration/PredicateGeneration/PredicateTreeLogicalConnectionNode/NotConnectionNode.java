package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode;

public class NotConnectionNode extends PredicateTreeLogicalConnectionNode {
    NotConnectionNode() {
        this.XPathExpr = "not";
    }

    @Override
    public String toString() {
        return this.XPathExpr + "(" + childList.get(0) + ")";
    }

    @Override
    public NotConnectionNode newInstance() {
        return new NotConnectionNode();
    }
}
