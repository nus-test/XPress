package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class SecondsFromDurationFunctionNode extends PredicateTreeFunctionNode {
    SecondsFromDurationFunctionNode() {
        this.datatype = XMLDatatype.INTEGER;
        this.XPathExpr = "seconds-from-duration";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public SecondsFromDurationFunctionNode newInstance() {
        return new SecondsFromDurationFunctionNode();
    }

    @Override
    public String calculationString() {
        return XPathExpr + "(xs:duration('" + childList.get(0).dataContent + "'))";
    }
}
