package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class LastFunctionNode extends InformationTreeDirectContentFunctionNode {
    LastFunctionNode() {
        functionExpr = "last";
    }

    @Override
    public String getCurrentLevelCalculationString() {
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            return getSequenceCalculationString();
        return "count(" + childList.get(0).getXPathExpression(false) + ")";
    }

    @Override
    public LastFunctionNode newInstance() {
        return new LastFunctionNode();
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        }
        else {
            datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            datatypeRecorder.subDatatype = XMLDatatype.INTEGER;
        }
    }
}
