package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class NormalizeSpaceFunctionNode extends PredicateTreeFunctionNode {
    NormalizeSpaceFunctionNode() {
        this.datatype = XMLDatatype.STRING;
        XPathExpr = "normalize-space";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public NormalizeSpaceFunctionNode newInstance() {
        return new NormalizeSpaceFunctionNode();
    }

    @Override
    public String calculationString() { return XPathExpr + "(\"" + childList.get(0).dataContent + "\")"; }
}
