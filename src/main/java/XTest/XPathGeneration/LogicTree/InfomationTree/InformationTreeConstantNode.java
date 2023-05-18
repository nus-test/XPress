package XTest.XPathGeneration.LogicTree.InfomationTree;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

public class InformationTreeConstantNode extends InformationTreeNode {
    public InformationTreeConstantNode() {}

    /**
     * Create new constant node with base type and string expression of context (raw value)
     * @param datatype
     * @param context
     */
    public InformationTreeConstantNode(XMLDatatype datatype, String context) {
        this.datatypeRecorder.xmlDatatype = datatype;
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
        this.datatypeRecorder.xmlDatatype = datatype;
        this.datatypeRecorder.subDatatype = subDatatype;
        this.length = length;
        this.context = context;
    }

    /**
     * Get XPath expression of current context. e.g. for boolean value "false" -> "false()"
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return
     */
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode) {
        return XMLDatatype.wrapExpression(context, this.datatypeRecorder.xmlDatatype);
    }
}
