package XPress.XPathGeneration.LogicTree.InformationTree;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XPress.TestException.DebugErrorException;
import XPress.XPathGeneration.LogicTree.LogicTreeNode;

public class InformationTreeContextNode extends InformationTreeNode {
    /**
     * If set to true, is the dummy context node generated for Map function.
     */
    public boolean dummyContext = false;
    /**
     * If is a dummy context node, contains the calculateString of the original node.
     */
    public String dummyCalculateString = null;

    public InformationTreeContextNode() {}

    public InformationTreeContextNode(XMLDatatypeComplexRecorder recorder) {
        this.datatypeRecorder = recorder;
    }

    public InformationTreeContextNode(XMLDatatypeComplexRecorder recorder, InformationTreeContext context) {
        this.datatypeRecorder = recorder;
        this.context = context;
    }

    public InformationTreeContextNode(InformationTreeContextNode contextNode) {
        transfer(contextNode);
    }

    public void transfer(InformationTreeContextNode contextNode) {
        dummyContext = contextNode.dummyContext;
        dummyCalculateString = contextNode.dummyCalculateString;
        contextInfo = new InformationTreeContextInfo(contextNode.getContextInfo());
        datatypeRecorder = new XMLDatatypeComplexRecorder(contextNode.datatypeRecorder);
        context = new InformationTreeContext(contextNode.getContext());
        XPathExpr = contextNode.XPathExpr;
    }

    // For context node, XPathExpr refers to the sub selection XPath expression.
    // If referring to current node, should be node selection expression.
    public void setXPath(String XPath) {
        this.XPathExpr = XPath;
    }

    public void setSelfContextFlag(boolean selfContextFlag) {
        getContextInfo().selfContext = selfContextFlag;
    }

    public void setStarredNodeId(int starredNodeId) {
        getContextInfo().starredNodeId = starredNodeId;
    }

    @Override
    public InformationTreeContextNode newInstance() {
        return new InformationTreeContextNode();
    }

    @Override
    public String getCalculationString(LogicTreeNode parentNode, boolean checkImpact) {
        if(dummyContext)
            return dummyCalculateString;
        String calculationStr = "//*[@id=\"" + getContextInfo().starredNodeId + "\"]";
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            calculationStr += "/" + XPathExpr;
        return calculationStr;
    }

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        if(calculateString)
            return getCalculationString(parentNode, false);
        return XPathExpr;
    }
}
