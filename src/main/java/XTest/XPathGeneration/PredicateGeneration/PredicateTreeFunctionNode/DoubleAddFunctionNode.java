package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import static java.lang.Math.abs;

public class DoubleAddFunctionNode extends PredicateTreeFunctionNode {
    DoubleAddFunctionNode() {
        datatype = XMLDatatype.DOUBLE;
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        double currentValue = Double.parseDouble(inputNode.dataContent);
        double addValue = Double.parseDouble(value);
        if(abs(currentValue + addValue) < 0.5) {
            if(addValue > 0) value = "-" + value;
            else value = value.substring(1);
        }
        else {
            if (currentValue > 10000 && Double.parseDouble(value) > 0) {
                value = "-" + value;
            } else if (currentValue < 10000 && Double.parseDouble(value) < 0) {
                value = value.substring(1);
            }
        }
        childList.add(new PredicateTreeConstantNode(XMLDatatype.DOUBLE, value));
    }

    @Override
    public DoubleAddFunctionNode newInstance() {
        return new DoubleAddFunctionNode();
    }

    @Override
    public String toString() {
        return childList.get(0) + " + " + childList.get(1);
    }

    @Override
    public String calculationString() { return childList.get(0).dataContent + " + " + childList.get(1).dataContent; }
}
