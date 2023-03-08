package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

public class ExistFunctionNode extends PredicateTreeFunctionNode {
    @Override
    public String toString() {
        return "exists(" + childList.toString() + ")";
    }
}
