package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class ExistFunctionNode extends PredicateTreeFunctionNode {
    ExistFunctionNode() {
        this.datatype = XMLDatatype.BOOLEAN;
    }

    @Override
    public String toString() {
        return "exists(" + childList.get(0).toString() + ")";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public ExistFunctionNode newInstance() {
        return new ExistFunctionNode();
    }

}
