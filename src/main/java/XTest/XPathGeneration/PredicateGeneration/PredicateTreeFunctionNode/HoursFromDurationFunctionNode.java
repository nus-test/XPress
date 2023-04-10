package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class HoursFromDurationFunctionNode extends PredicateTreeFunctionNode {
    HoursFromDurationFunctionNode() {
        this.datatype = XMLDatatype.INTEGER;
        this.XPathExpr = "hours-from-duration";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public HoursFromDurationFunctionNode newInstance() {
        return new HoursFromDurationFunctionNode();
    }

    @Override
    public String calculationString() {
        return XPathExpr + "(xs:duration('" + childList.get(0).dataContent + "'))";
    }
}
