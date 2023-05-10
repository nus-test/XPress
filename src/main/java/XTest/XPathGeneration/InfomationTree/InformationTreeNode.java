package XTest.XPathGeneration.InfomationTree;

import XTest.PrimitiveDatatype.XMLDatatype;

import java.util.List;

public abstract class InformationTreeNode {
    /**
     * Datatype of result of the current information tree node
     */
    XMLDatatype xmlDatatype;

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
    String length;

    /**
     * If subtree contains a context node is set to true.
     */
    boolean containsContext = false; // Check if subtree contains a context node
    /**
     * If within subtree an ancestor node of the context node is with calculated context value is set to true.
     */
    boolean containsContextConstant = false;
    int starredNodeId = -1;
    InformationTreeNode contextChildNode = null;
    List<InformationTreeNode> paramChildNode = null;

    /**
     *
     * @return The XPath expression represented by subtree of current information tree node.
     * e.g. for current XPath expression /A with a generated tree to express lower-case(@name) = "xx",
     * root node calling this method should return string "lower-case(@name) = "xx"".
     */
    String getXPathExpression() { return getXPathExpression(false); }

    /**
     *
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return The XPath expression represented by subtree of current information tree node.
     */
    String getXPathExpression(boolean returnConstant) { return null; }

    /**
     *
     * @return The calculation string which could compute the correct result for the starred node of
     * current information tree. For nodes which returns a sequence calculates the sequence length instead of
     * actual value.
     */
    String getCalculationString() {
        String calculationString = "";
        if(!containsContext) calculationString = getXPathExpression(false);
        else if(containsContextConstant) calculationString = getXPathExpression(true);
        else {
            calculationString = getCurrentLevelCalculationString();
        }
        if(xmlDatatype == XMLDatatype.SEQUENCE)
            calculationString = "count(" + calculationString + ")";
        return calculationString;
    }

    String getCurrentLevelCalculationString() {
        return null;
    }
}
