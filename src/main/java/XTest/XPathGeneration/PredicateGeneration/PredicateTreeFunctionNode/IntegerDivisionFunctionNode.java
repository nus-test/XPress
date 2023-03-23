package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class IntegerDivisionFunctionNode extends PredicateTreeFunctionNode{
    IntegerDivisionFunctionNode() {
        this.datatype = XMLDatatype.INTEGER;
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        String value = null;
        if(prob < 0.7 && Integer.parseInt(inputNode.dataContent) >= 2)
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(1, Integer.parseInt(inputNode.dataContent));
        else {
            value = XMLDatatype.INTEGER.getValueHandler().getValue(false);
        }
        if(Integer.parseInt(value) == 0)
            value = "2";
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode(XMLDatatype.INTEGER, value);
        childList.add(inputNode);
        childList.add(constantNode);
    }

    @Override
    public String toString() {
        return childList.get(0) + " idiv " + childList.get(1);
    }

    @Override
    public IntegerDivisionFunctionNode newInstance() {
        return new IntegerDivisionFunctionNode();
    }

    @Override
    public String calculationString() { return childList.get(0).dataContent + " idiv " + childList.get(1).dataContent; }

}
