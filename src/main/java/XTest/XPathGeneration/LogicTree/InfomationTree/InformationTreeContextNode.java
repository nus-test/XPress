package XTest.XPathGeneration.LogicTree.InfomationTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

public class InformationTreeContextNode extends InformationTreeNode {

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
    public String getCalculationString() {
        if(dataTypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            return "//*[@id=\"" + getContextInfo().starredNodeId + "\"]/" + XPathExpr + "";
        return null;
    }

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode) {
        return XPathExpr;
    }

    public InformationTreeContextNode() {
    }
}
