package XTest.XPathGeneration.LogicTree.InformationTree;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

public class InformationTreeContextNode extends InformationTreeNode {
    /**
     * If set to true, is the dummy context node generated for Map function.
     */
    public boolean dummyContext = false;
    /**
     * If is a dummy context node, contains the calculateString of the original node.
     */
    public String dummyCalculateString = null;

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

    public InformationTreeContextNode() {
    }

    public InformationTreeContextNode(XMLDatatypeComplexRecorder recorder) {
        this.datatypeRecorder = recorder;
    }

    public InformationTreeContextNode(XMLDatatypeComplexRecorder recorder, InformationTreeContext context) {
        this.datatypeRecorder = recorder;
        this.context = context;
    }
}
