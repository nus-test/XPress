package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import static java.lang.Math.abs;

public class DoubleMultiplicationFunctionNode extends PredicateTreeFunctionNode implements NumericalBinaryOperator {

    DoubleMultiplicationFunctionNode() {
        this.datatype = XMLDatatype.DOUBLE;
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        double currentValue = Double.parseDouble(inputNode.dataContent);
        double multiplicationValue;
        if(abs(currentValue) > 10000) {
            multiplicationValue = GlobalRandom.getInstance().nextDouble() + 0.1;
            if(multiplicationValue > 1) multiplicationValue = 1;
        }
        else {
            Integer pre = GlobalRandom.getInstance().nextInt(10) + 1;
            double last = GlobalRandom.getInstance().nextDouble();
            if(last < 0.0001) last = 0.0001;
            multiplicationValue = pre + last;
        }
        childList.add(new PredicateTreeConstantNode(XMLDatatype.DOUBLE, Double.toString(multiplicationValue)));
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
    public DoubleMultiplicationFunctionNode newInstance() {
        return new DoubleMultiplicationFunctionNode();
    }

    @Override
    public String calculationString() { return childList.get(0).dataContent + " * " + childList.get(1).dataContent; }
}
