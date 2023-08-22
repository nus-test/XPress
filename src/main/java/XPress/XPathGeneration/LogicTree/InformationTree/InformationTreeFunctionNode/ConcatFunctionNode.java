package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class ConcatFunctionNode extends InformationTreeFunctionNode {
    public ConcatFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLString.getInstance();
        functionExpr = "concat";
    }

    @Override
    public ConcatFunctionNode newInstance() {
        return new ConcatFunctionNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        if(fillContentParameterBySubRoot(XMLString.getInstance())) return;
        fillContentParametersRandom(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String randomString = XMLString.getInstance().getValueHandler().getValue(false);
        InformationTreeConstantNode constantNode = new InformationTreeConstantNode(XMLString.getInstance(), randomString);
        childList.add(constantNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
    }
}
