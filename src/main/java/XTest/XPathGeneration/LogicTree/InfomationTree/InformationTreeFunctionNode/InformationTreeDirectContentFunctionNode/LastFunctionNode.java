package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class LastFunctionNode extends InformationTreeDirectContentFunctionNode {
    LastFunctionNode() {
        functionExpr = "last";
    }

    @Override
    public String getCurrentLevelCalculationString() {
        if(dataTypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
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
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            dataTypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        }
        else {
            dataTypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            dataTypeRecorder.subDatatype = XMLDatatype.INTEGER;
        }
    }
}
