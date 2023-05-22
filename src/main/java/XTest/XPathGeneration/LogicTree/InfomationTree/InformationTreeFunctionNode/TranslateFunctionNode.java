package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class TranslateFunctionNode extends InformationTreeFunctionNode {
    public TranslateFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "translate";
    }

    @Override
    public void fillContentParameters(InformationTreeNode childNode) {
        fillContentParametersRandom(childNode);
    }

    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) {
        String mapStr = XMLDatatype.STRING.getValueHandler().getValue();
        String transStr = XMLDatatype.STRING.getValueHandler().getValue();
        InformationTreeConstantNode mapNode = new InformationTreeConstantNode(XMLDatatype.STRING, mapStr);
        InformationTreeConstantNode transNode = new InformationTreeConstantNode(XMLDatatype.STRING, transStr);
        childList.add(mapNode);
        childList.add(transNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.STRING;
    }

    @Override
    public TranslateFunctionNode newInstance() {
        return new TranslateFunctionNode();
    }
}
