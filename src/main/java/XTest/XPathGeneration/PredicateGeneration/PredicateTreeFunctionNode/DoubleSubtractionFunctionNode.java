package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import static java.lang.Math.abs;

public class DoubleSubtractionFunctionNode extends PredicateTreeFunctionNode implements NumericalBinaryOperator {
    DoubleSubtractionFunctionNode() {
        datatype = XMLDatatype.DOUBLE;
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        double currentValue = Double.parseDouble(inputNode.dataContent);
        double subtractValue = Double.parseDouble(value);
        if(abs(currentValue - subtractValue) < 0.5) {
            if(subtractValue < 0) value = value.substring(1);
            else value = "-" + value;
        }
        else {
            if (currentValue > 10000 && subtractValue < 0) {
                value = value.substring(1);
            } else if (currentValue < 10000 && subtractValue > 0) {
                value = "-" + value;
            }
        }
        childList.add(new PredicateTreeConstantNode(XMLDatatype.DOUBLE, value));
    }

    @Override
    public DoubleSubtractionFunctionNode newInstance() {
        return new DoubleSubtractionFunctionNode();
    }

    @Override
    public String toString() {
        return childList.get(0) + " - " + childList.get(1);
    }

    @Override
    public String calculationString() { return childList.get(0).dataContent + " - " + childList.get(1).dataContent; }
}
