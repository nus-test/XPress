package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class IntegerMultiplicationFunctionNode extends PredicateTreeFunctionNode implements NumericalBinaryOperator {
    IntegerMultiplicationFunctionNode() {
        this.datatype = XMLDatatype.INTEGER;
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        String value;
        Integer inputValue = Math.abs(Integer.parseInt(inputNode.dataContent));
        if(inputValue == 0) {
            value = Integer.toString(GlobalRandom.getInstance().nextInt());
        }
        else {
            Integer boundValue = Integer.MAX_VALUE / inputValue;
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(-boundValue, boundValue);
            if (value.equals("0"))
                value = Integer.toString(1);
        }
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode(XMLDatatype.INTEGER, value);
        childList.add(inputNode);
        childList.add(constantNode);
    }

    @Override
    public String toString() {
        String leftChild = wrapNumericalBinaryFunctionExpr(childList.get(0), this);
        String rightChild = wrapNumericalBinaryFunctionExpr(childList.get(1), this);
        return leftChild + " * " + rightChild;
    }

    @Override
    public String toStringOmit() {
        String rightChild = wrapNumericalBinaryFunctionExpr(childList.get(1), this);
        return "(. * " + rightChild + ")";
    }

    @Override
    public IntegerMultiplicationFunctionNode newInstance() {
        return new IntegerMultiplicationFunctionNode();
    }

    @Override
    public String calculationString() { return childList.get(0).dataContent + " * " + childList.get(1).dataContent; }
}
