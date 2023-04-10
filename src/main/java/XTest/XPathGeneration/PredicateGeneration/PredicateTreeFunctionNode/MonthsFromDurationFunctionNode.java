package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class MonthsFromDurationFunctionNode extends PredicateTreeFunctionNode {
    MonthsFromDurationFunctionNode() {
        this.datatype = XMLDatatype.INTEGER;
        this.XPathExpr = "months-from-duration";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public MonthsFromDurationFunctionNode newInstance() {
        return new MonthsFromDurationFunctionNode();
    }

    @Override
    public String calculationString() {
        return XPathExpr + "(xs:duration('" + childList.get(0).dataContent + "'))";
    }
}
