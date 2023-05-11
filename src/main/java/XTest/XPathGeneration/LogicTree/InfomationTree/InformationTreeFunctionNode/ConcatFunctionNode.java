package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class ConcatFunctionNode extends InformationTreeFunctionNode {
    ConcatFunctionNode() {
        this.dataTypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "concat";
    }

    @Override
    public ConcatFunctionNode newInstance() {
        return new ConcatFunctionNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        fillContentsRandom(childNode);
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        String randomString = XMLDatatype.STRING.getValueHandler().getValue(false);
        InformationTreeConstantNode constantNode = new InformationTreeConstantNode(XMLDatatype.STRING, randomString);
        childList.add(constantNode);
    }
}
