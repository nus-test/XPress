package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import static java.lang.Math.abs;

public class DoubleDivisionFunctionNode extends PredicateTreeFunctionNode {
    DoubleDivisionFunctionNode() {
        this.datatype = XMLDatatype.DOUBLE;
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        double currentValue = Double.parseDouble(inputNode.dataContent);
        double divisionValue;
        if(abs(currentValue) > 1) {
            Integer pre = GlobalRandom.getInstance().nextInt(10);
            Integer last = GlobalRandom.getInstance().nextInt(10);
            divisionValue = Double.parseDouble(pre + "." + last);
        }
        else {
            divisionValue = GlobalRandom.getInstance().nextDouble() + 0.5;
            if(divisionValue > 1.2) divisionValue = 1;
        }
        String divisionValueStr = Double.toString(divisionValue);
        childList.add(new PredicateTreeConstantNode(XMLDatatype.DOUBLE, divisionValueStr));
    }

    @Override
    public DoubleDivisionFunctionNode newInstance() {
        return new DoubleDivisionFunctionNode();
    }

    @Override
    public String toString() {
        return childList.get(0) + " div " + childList.get(1);
    }
}
