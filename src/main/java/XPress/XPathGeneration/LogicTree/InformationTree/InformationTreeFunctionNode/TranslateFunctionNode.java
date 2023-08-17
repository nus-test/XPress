package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class TranslateFunctionNode extends InformationTreeFunctionNode {
    public TranslateFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "translate";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {;
        String mapStr = XMLDatatype.STRING.getValueHandler().mutateValue(childNode.context.context);
        String transStr = XMLDatatype.STRING.getValueHandler().getValue();
        InformationTreeConstantNode mapNode = new InformationTreeConstantNode(XMLDatatype.STRING, mapStr);
        InformationTreeConstantNode transNode = new InformationTreeConstantNode(XMLDatatype.STRING, transStr);
        childList.add(mapNode);
        childList.add(transNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String mapStr = XMLDatatype.STRING.getValueHandler().getValue();
        String transStr = XMLDatatype.STRING.getValueHandler().getValue();
        InformationTreeConstantNode mapNode = new InformationTreeConstantNode(XMLDatatype.STRING, mapStr);
        InformationTreeConstantNode transNode = new InformationTreeConstantNode(XMLDatatype.STRING, transStr);
        childList.add(mapNode);
        childList.add(transNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }

    @Override
    public TranslateFunctionNode newInstance() {
        return new TranslateFunctionNode();
    }
}