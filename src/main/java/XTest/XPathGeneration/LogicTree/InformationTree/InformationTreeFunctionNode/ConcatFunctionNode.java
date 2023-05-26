package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

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
    protected void fillContentParameters(InformationTreeNode childNode) {
        fillContentParametersRandom(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String randomString = XMLDatatype.STRING.getValueHandler().getValue(false);
        InformationTreeConstantNode constantNode = new InformationTreeConstantNode(XMLDatatype.STRING, randomString);
        childList.add(constantNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.STRING;
    }
}
