package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class UpperCaseFunctionNode extends PredicateTreeFunctionNode {

    UpperCaseFunctionNode() {
        this.datatype = XMLDatatype.STRING;
    }
    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public String toString() {
        return "upper-case(" + childList.get(0).toString() + ")";
    }

    @Override
    public UpperCaseFunctionNode newInstance() {
        return new UpperCaseFunctionNode();
    }
}
