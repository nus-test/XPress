package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
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
        if(fillContentParameterBySubRoot(XMLDatatype.STRING)) return;
        fillContentParametersRandom(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String randomString = XMLDatatype.STRING.getValueHandler().getValue(false);
        InformationTreeConstantNode constantNode = new InformationTreeConstantNode(XMLDatatype.STRING, randomString);
        childList.add(constantNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }
}
