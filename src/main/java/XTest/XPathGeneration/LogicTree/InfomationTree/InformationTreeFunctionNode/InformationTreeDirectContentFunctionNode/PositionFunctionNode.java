package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class PositionFunctionNode extends InformationTreeDirectContentFunctionNode {
    public PositionFunctionNode() {
        functionExpr = "position";
    }

    @Override
    public String getCurrentLevelCalculationString() {
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            return getSequenceCalculationString();
        return "index-of(" + childList.get(0).getXPathExpression(false)
                + ", //*[id=\"" + childList.get(0).context + "\"])";
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

    @Override
    public PositionFunctionNode newInstance() {
        return new PositionFunctionNode();
    }
}
