package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class EndsWithFunctionNode extends InformationTreeFunctionNode {
    public EndsWithFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        functionExpr = "ends-with";
    }

    @Override
    public void fillContentParameters(InformationTreeNode childNode) {
        fillContentsWithGivenContext(childNode, childNode.context);
    }

    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) {
        fillContentsWithGivenContext(childNode, XMLDatatype.STRING.getValueHandler().getValue(false));
    }

    private void fillContentsWithGivenContext(InformationTreeNode childNode, String str) {
        double prob = GlobalRandom.getInstance().nextDouble();
        int startIndex = GlobalRandom.getInstance().nextInt(str.length());
        String endStr = str.substring(startIndex);
        if(prob < 0.2) {
            endStr = XMLDatatype.STRING.getValueHandler().mutateValue(endStr);
        }
        childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, endStr));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.STRING;
    }

    @Override
    public EndsWithFunctionNode newInstance() {
        return new EndsWithFunctionNode();
    }


}
