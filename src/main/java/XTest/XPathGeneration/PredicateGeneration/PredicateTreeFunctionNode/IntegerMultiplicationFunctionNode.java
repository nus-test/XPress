package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class IntegerMultiplicationFunctionNode extends PredicateTreeFunctionNode {
    static {
        IntegerMultiplicationFunctionNode multiplicationFunctionNode = new IntegerMultiplicationFunctionNode();
        PredicateTreeFunctionNode.insertFunctionToMap(multiplicationFunctionNode, XMLDatatype.INTEGER);
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        String value;
        Integer inputValue = Math.abs(Integer.parseInt(inputNode.dataContent));
        Integer boundValue = Integer.MAX_VALUE / inputValue;
        value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                getRandomValueBounded(-boundValue,boundValue);
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode(XMLDatatype.INTEGER, value);
        childList.add(inputNode);
        childList.add(constantNode);
    }

    @Override
    public String toString() {
        return childList.get(0) + "*" + childList.get(1);
    }

    @Override
    public IntegerMultiplicationFunctionNode newInstance() {
        return new IntegerMultiplicationFunctionNode();
    }
}
