package XTest.XPathGeneration.LogicTree.InformationTree;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import org.apache.commons.lang3.tuple.Pair;

public class InformationTreeConstantNode extends InformationTreeNode {
    public InformationTreeConstantNode() {}

    /**
     * Create new constant node with base type and string expression of context (raw value)
     * @param datatype
     * @param context
     */
    public InformationTreeConstantNode(XMLDatatype datatype, String context) {
        this.datatypeRecorder.xmlDatatype = datatype;
        XPathExpr = getContext().context = context;
    }

    /**
     * Create new constant node with sequence of given type, context contains length and XPath expression
     * @param recorder
     * @param context
     */
    public InformationTreeConstantNode(XMLDatatypeComplexRecorder recorder, Pair<Integer, String> context) {
        this.datatypeRecorder = recorder;
        getContext().context = Integer.toString(context.getLeft());
        XPathExpr = context.getRight();
    }

    public InformationTreeConstantNode(XMLDatatypeComplexRecorder recorder, String context) {
        this.datatypeRecorder = recorder;
        XPathExpr = getContext().context = context;
    }

    public InformationTreeConstantNode(XMLDatatypeComplexRecorder recorder, InformationTreeContext context) {
        this.datatypeRecorder = recorder;
        this.context = context;
        XPathExpr = context.context;
    }


    /**
     * Create new constant node for sequence constants. e.g. 1 to 5
     * @param datatype
     * @param subDatatype
     * @param context
     */
    public InformationTreeConstantNode(XMLDatatype datatype, XMLDatatype subDatatype, String context) {
        this.datatypeRecorder.xmlDatatype = datatype;
        this.datatypeRecorder.subDatatype = subDatatype;
        XPathExpr = getContext().context = context;
    }

    @Override
    public InformationTreeConstantNode newInstance() {
        return new InformationTreeConstantNode();
    }

    /**
     * Get XPath expression of current context. e.g. for boolean value "false" -> "false()"
     * @param returnConstant Whether to return constant context when approached or always reach the leaf nodes.
     * @return
     */
    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        return XMLDatatype.wrapExpression(XPathExpr, this.datatypeRecorder.xmlDatatype);
    }
}
