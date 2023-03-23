package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class IntegerSubtractionFunctionNode extends PredicateTreeFunctionNode {

    IntegerSubtractionFunctionNode() {
        this.datatype = XMLDatatype.INTEGER;
    }
    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        String value = null;
        Integer inputValue = Math.abs(Integer.parseInt(inputNode.dataContent));
        if(inputValue < 0)
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(Integer.MIN_VALUE - inputValue);
        else {
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(inputValue - Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode(XMLDatatype.INTEGER, value);
        childList.add(inputNode);
        childList.add(constantNode);
    }

    @Override
    public String toString() {
        return childList.get(0) + " - " + childList.get(1);
    }

    @Override
    public IntegerSubtractionFunctionNode newInstance() {
        return new IntegerSubtractionFunctionNode();
    }

    @Override
    public String calculationString() { return childList.get(0).dataContent + " - " + childList.get(1).dataContent; }
}
