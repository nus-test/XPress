package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class TranslateFunctionNode extends InformationTreeFunctionNode {
    public TranslateFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLString.getInstance();
        functionExpr = "translate";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {;
        String mapStr = XMLString.getInstance().getValueHandler().mutateValue(childNode.context.context);
        String transStr = XMLString.getInstance().getValueHandler().getValue();
        InformationTreeConstantNode mapNode = new InformationTreeConstantNode(XMLString.getInstance(), mapStr);
        InformationTreeConstantNode transNode = new InformationTreeConstantNode(XMLString.getInstance(), transStr);
        childList.add(mapNode);
        childList.add(transNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String mapStr = XMLString.getInstance().getValueHandler().getValue();
        String transStr = XMLString.getInstance().getValueHandler().getValue();
        InformationTreeConstantNode mapNode = new InformationTreeConstantNode(XMLString.getInstance(), mapStr);
        InformationTreeConstantNode transNode = new InformationTreeConstantNode(XMLString.getInstance(), transStr);
        childList.add(mapNode);
        childList.add(transNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
    }

    @Override
    public TranslateFunctionNode newInstance() {
        return new TranslateFunctionNode();
    }
}
