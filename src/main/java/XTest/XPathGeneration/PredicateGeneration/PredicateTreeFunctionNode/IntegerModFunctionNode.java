package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class IntegerModFunctionNode extends PredicateTreeFunctionNode {
    IntegerModFunctionNode() {
        this.datatype = XMLDatatype.INTEGER;
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        String value = null;
        Integer inputValue = Integer.parseInt(inputNode.dataContent);
        if(inputValue < 0) {
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(Integer.MIN_VALUE - inputValue, Integer.MAX_VALUE);
        }
        else {
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(Integer.MAX_VALUE - inputValue);
        }
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode(XMLDatatype.INTEGER, value);
        childList.add(inputNode);
        childList.add(constantNode);
    }

    @Override
    public PredicateTreeFunctionNode newInstance() {
        return new IntegerModFunctionNode();
    }

    @Override
    public String toString() {
        return childList.get(0) + " mod " + childList.get(1);
    }
}