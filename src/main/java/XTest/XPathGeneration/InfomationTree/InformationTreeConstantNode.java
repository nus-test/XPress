package XTest.XPathGeneration.InfomationTree;

import XTest.PrimitiveDatatype.XMLDatatype;

public class InformationTreeConstantNode extends InformationTreeNode {
    /**
     * Create new constant node with base type and string expression of context (raw value)
     * @param datatype
     * @param context
     */
    public InformationTreeConstantNode(XMLDatatype datatype, String context) {
        this.xmlDatatype = datatype;
        this.context = context;
    }

    /**
     * Create new constant node for sequence constants. e.g. 1 to 5
     * @param datatype
     * @param subDatatype
     * @param length
     * @param context
     */
    public InformationTreeConstantNode(XMLDatatype datatype, XMLDatatype subDatatype, int length, String context) {
        this.xmlDatatype = datatype;
        this.subDatatype = subDatatype;
        this.length = length;
        this.context = context;
    }

    /**
     * Get XPath expression of current context. e.g. for boolean value "false" -> "false()"
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return
     */
    String getXPathExpression(boolean returnConstant) {
        return XMLDatatype.wrapExpression(context, xmlDatatype);
    }
}
