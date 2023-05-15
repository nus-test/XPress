package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.NumericalBinaryOperator;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import static java.lang.Math.abs;

public class DoubleAddFunctionNode extends InformationTreeFunctionNode implements NumericalBinaryOperator {
    DoubleAddFunctionNode() {
        this.dataTypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
    }

    @Override
    public DoubleAddFunctionNode newInstance() {
        return new DoubleAddFunctionNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(childNode.context == null) {
            fillContentsRandom(childNode);
            return;
        }
        childList.add(childNode);
        double currentValue = Double.parseDouble(childNode.context);
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        double addValue = Double.parseDouble(value);
        if(abs(currentValue + addValue) < 0.5) {
            if(addValue > 0) value = "-" + value;
            else value = value.substring(1);
        }
        else {
            if (currentValue > 10000 && Double.parseDouble(value) > 0) {
                value = "-" + value;
            } else if (currentValue < 10000 && Double.parseDouble(value) < 0) {
                value = value.substring(1);
            }
        }
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, value));
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        // TODO: Control interval of randomly generated value to avoid overflow if necessary
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DOUBLE;
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        String returnString = getXPathExpressionCheck(returnConstant);
        if(returnString != null) return returnString;
        return childList.get(0).getXPathExpression(returnConstant) + " + " +
                childList.get(1).getXPathExpression(returnConstant);
    }
 }
