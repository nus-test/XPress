package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.GlobalRandom;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import static java.lang.Math.abs;

@FunctionV1
public class DoubleMultiplicationFunctionNode extends BinaryNumericalOperatorFunctionNode {

    public DoubleMultiplicationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDouble.getInstance();
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
        childList.add(new InformationTreeConstantNode(XMLDouble.getInstance(), Double.toString(multiplicationValue)));
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
        childList.add(new InformationTreeConstantNode(XMLDouble.getInstance(), multiplicationValueStr));
        inheritContextChildInfo(childNode);
    }

    @Override
    public DoubleMultiplicationFunctionNode newInstance() {
        return new DoubleMultiplicationFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDouble;
    }
}
