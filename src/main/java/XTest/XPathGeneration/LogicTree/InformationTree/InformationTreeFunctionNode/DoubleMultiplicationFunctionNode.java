package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import static java.lang.Math.abs;

public class DoubleMultiplicationFunctionNode extends BinaryNumericalOperatorFunctionNode {

    public DoubleMultiplicationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        priorityLevel = 3;
        functionExpr = "*";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        double currentValue = Double.parseDouble(childNode.getContext().context);
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
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, Double.toString(multiplicationValue)));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        double multiplicationValue;
        if(prob < 0.6) {
            multiplicationValue = GlobalRandom.getInstance().nextDouble() + 0.1;
            if(multiplicationValue > 1) multiplicationValue = 1;
        }
        else {
            Integer pre = GlobalRandom.getInstance().nextInt(10) + 1;
            double last = GlobalRandom.getInstance().nextDouble();
            if(last < 0.0001) last = 0.0001;
            multiplicationValue = pre + last;
        }
        String multiplicationValueStr = Double.toString(multiplicationValue);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, multiplicationValueStr));
        inheritContextChildInfo(childNode);
    }

    @Override
    public DoubleMultiplicationFunctionNode newInstance() {
        return new DoubleMultiplicationFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
}
