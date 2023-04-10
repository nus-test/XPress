package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class YearsFromDurationFunctionNode extends PredicateTreeFunctionNode {
    YearsFromDurationFunctionNode() {
        this.datatype = XMLDatatype.INTEGER;
        this.XPathExpr = "years-from-duration";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public YearsFromDurationFunctionNode newInstance() {
        return new YearsFromDurationFunctionNode();
    }

    @Override
    public String calculationString() {
        return XPathExpr + "(xs:duration('" + childList.get(0).dataContent + "'))";
    }
}
