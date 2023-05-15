package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.NumericalBinaryOperator;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;
import org.apache.xpath.operations.Bool;

import static XTest.StringUtils.getListString;

public abstract class InformationTreeFunctionNode extends InformationTreeNode {
    public String functionExpr;


    abstract public InformationTreeFunctionNode newInstance();

    /**
     * Fill the content parameters of current function node with given child node as context.
     * Is implemented with the best effort to fit parameters which is considered reasonable and if is boolean valued
     * to be evaluated true for given context.
     * @param childNode Given context.
     */
    abstract public void fillContents(InformationTreeNode childNode);

    /**
     * Fill the content parameters of current function node with given child node as context.
     * The given context is either not evaluable or sequence, fill the remaining contents randomly.
     * @param childNode Given context.
     */
    abstract public void fillContentsRandom(InformationTreeNode childNode);
    /**
     *
     * @return String expression of simplified function with current context.
     * e.g. upper-case("a") -> upper-case(.), current context represented by '.'.
     * Default for function with pattern "function(., param_a, param_b)"
     */
    public String getCurrentContextFunctionExpr() {
        return functionExpr + "(., " + getListString(childList.subList(1, childList.size())) + ")";
    }

    /**
     *
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return The XPath expression represented by subtree of current information tree node.
     */
    @Override
    public String getXPathExpression(boolean returnConstant) {
        String returnString = getXPathExpressionCheck(returnConstant);
        if(returnString != null) return returnString;
        return getDefaultFunctionXPathExpression(returnConstant);
    }

    public String getDefaultFunctionXPathExpression(boolean returnConstant) {
        String builder = functionExpr + "(";
        for(int i = 0; i < childList.size(); i ++) {
            if(i != 0) builder += ", ";
            builder += childList.get(i).getXPathExpression(returnConstant);
        }
        return builder;
    }

    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return checkContextAcceptability(childNode, childNode.dataTypeRecorder);
    }

    abstract public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder);
}
