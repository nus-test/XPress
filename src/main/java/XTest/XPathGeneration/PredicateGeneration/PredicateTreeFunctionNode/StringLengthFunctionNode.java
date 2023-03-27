package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class StringLengthFunctionNode extends PredicateTreeFunctionNode {
    StringLengthFunctionNode() {
        datatype = XMLDatatype.INTEGER;
        XPathExpr = "string-length";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public StringLengthFunctionNode newInstance() {
        return new StringLengthFunctionNode();
    }

    @Override
    public String calculationString() { return XPathExpr + "(\"" + childList.get(0).dataContent + "\")"; }
}
