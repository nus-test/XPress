package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class DoubleAddFunctionNode extends PredicateTreeFunctionNode {
    DoubleAddFunctionNode() {
        datatype = XMLDatatype.DOUBLE;
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        double currentValue = Double.parseDouble(inputNode.dataContent);
        if(currentValue > 10000 && Double.parseDouble(value) > 0) {
            value = "-" + value;
        }
        else if(currentValue < 10000 && Double.parseDouble(value) < 0) {
            value = value.substring(1);
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
}
