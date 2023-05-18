package XTest.XPathGeneration.LogicTree.InfomationTree;

import XTest.PrimitiveDatatype.XMLDatatype;

public class InformationTreeContextNode extends InformationTreeNode {
    /**
     * Record down the XPathPrefix to produce node candidates before applying current information tree selection.
     * e.g. /A1/B1, /A1/(B1, C1)
     */
    public String XPathPrefix;

    // For context node, XPathExpr refers to the sub selection XPath expression.
    // If referring to current node, should be node selection expression.
    public void setXPath(String XPath) {
        this.XPathExpr = XPath;
    }

    public void setSelfContextFlag(boolean selfContextFlag) {
        this.selfContext = selfContextFlag;
    }

    public void setStarredNodeId(int starredNodeId) {
        this.starredNodeId = starredNodeId;
    }

    @Override
    public String getCalculationString() {
        if(dataTypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            return "//*[@id=\"" + starredNodeId + "\"]/" + XPathExpr + "";
        return null;
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        return XPathExpr;
    }
}
