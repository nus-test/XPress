package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

import static XTest.StringUtils.getListString;

public abstract class InformationTreeFunctionNode extends InformationTreeNode {
    String functionExpr;


    abstract public InformationTreeFunctionNode newInstance();

    /**
     * Fill the content parameters of current function node with given child node as context.
     * Is implemented with the best effort to fit parameters which is considered reasonable and if is boolean valued
     * to be evaluated true for given context.
     * @param childNode Given context.
     */
    abstract public void fillContents(InformationTreeNode childNode);
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
}
