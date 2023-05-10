package XTest.XPathGeneration.InfomationTree;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import java.util.ArrayList;
import java.util.List;

public abstract class InformationTreeNode {
    /**
     * Cached XPathExpr with no constant substitution.
     */
    String XPathExpr = null;

    /**
     * Datatype of result of the current information tree node
     */
    protected XMLDatatype xmlDatatype;

    /**
     * If tree node represents a sequence, denotes the datatype of the elements in sequence
     */
    XMLDatatype subDatatype;

    /**
     * If is calculable, contains the real value of evaluated context for the starred node
     */
    String context;

    /**
     * If is sequence type, contains the length of sequence for the starred node
     */
    int length;

    /**
     * If within subtree there is no context node or an ancestor node of the context node is
     * with calculated context value is set to true.
     */
    boolean containsContextConstant = false;

    /**
     * Starred node id which is recorded in the unique context node.
     */
    int starredNodeId = -1;
    /**
     * If set to true unique context node refers to the starred node in context of XPath prefix.
     * Else refers to a derived sequence from the starred node with itself as the context.
     */
    boolean selfContext = true;

    public List<InformationTreeNode> childList = new ArrayList<>();

    /**
     *
     * @return The XPath expression represented by subtree of current information tree node.
     * e.g. for current XPath expression /A with a generated tree to express lower-case(@name) = "xx",
     * root node calling this method should return string "lower-case(@name) = "xx"".
     */
    String getXPathExpression() {
        if(XPathExpr != null) return XPathExpr;
        XPathExpr = getXPathExpression(false);
        return XPathExpr;
    }

    /**
     *
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return The XPath expression represented by subtree of current information tree node.
     */
    String getXPathExpression(boolean returnConstant) { return null; }

    @Override
    public String toString() {
        return getXPathExpression();
    }

    /**
     * Cache the XPath expression calculated for current subtree with no constant replacement.
     * @param XPathExpr Calculated XPath expression
     * @param returnConstant XPath expression calculated w/o constant replacement
     */
    void cacheXPathExpression(String XPathExpr, boolean returnConstant) {
        if(!returnConstant) this.XPathExpr = XPathExpr;
    }

    /**
     *
     * @return The calculation string which could compute the correct result for the starred node of
     * current information tree. For nodes which returns a sequence calculates the sequence length instead of
     * actual value.
     */
    String getCalculationString() throws DebugErrorException {
        String calculationString = "";
        if(containsContextConstant) calculationString = getXPathExpression(true);
        else if(!selfContext) {
            calculationString = "//*[id = \"" + starredNodeId + "\"]/" + getXPathExpression();
        } else if(this instanceof InformationTreeDirectContentFunctionNode)
            calculationString = ((InformationTreeDirectContentFunctionNode) this).getCurrentLevelCalculationString();
        else throw new DebugErrorException();
        if(xmlDatatype == XMLDatatype.SEQUENCE)
            calculationString = "count(" + calculationString + ")";
        return calculationString;
    }

    /**
     *
     * @return Context of subtree represented by current node.
     */
    String getContext() {
        return context;
    }
}
