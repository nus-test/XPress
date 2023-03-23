package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class UpperCaseFunctionNode extends PredicateTreeFunctionNode {

    UpperCaseFunctionNode() {
        this.datatype = XMLDatatype.STRING;
        this.XPathExpr = "upper-case";
    }
    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public UpperCaseFunctionNode newInstance() {
        return new UpperCaseFunctionNode();
    }

    @Override
    public String calculationString() { return XPathExpr + "(\"" + childList.get(0).dataContent + "\")"; }

}
