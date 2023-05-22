package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class ConcatFunctionNode extends InformationTreeFunctionNode {
    public ConcatFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "concat";
    }

    @Override
    public ConcatFunctionNode newInstance() {
        return new ConcatFunctionNode();
    }

    @Override
    public void fillContentParameters(InformationTreeNode childNode) {
        fillContentParametersRandom(childNode);
    }

    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) {
        String randomString = XMLDatatype.STRING.getValueHandler().getValue(false);
        InformationTreeConstantNode constantNode = new InformationTreeConstantNode(XMLDatatype.STRING, randomString);
        childList.add(constantNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.STRING;
    }
}
