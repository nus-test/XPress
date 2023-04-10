package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class MinutesFromDurationFunctionNode extends PredicateTreeFunctionNode {
    MinutesFromDurationFunctionNode() {
        this.datatype = XMLDatatype.INTEGER;
        this.XPathExpr = "minutes-from-duration";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public MinutesFromDurationFunctionNode newInstance() {
        return new MinutesFromDurationFunctionNode();
    }

    @Override
    public String calculationString() {
        return XPathExpr + "(xs:duration('" + childList.get(0).dataContent + "'))";
    }
}
