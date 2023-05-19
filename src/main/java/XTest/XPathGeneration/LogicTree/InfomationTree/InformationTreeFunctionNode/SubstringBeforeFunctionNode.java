package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class SubstringBeforeFunctionNode extends InformationTreeFunctionNode {
    public SubstringBeforeFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "substring-before";
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(!childNode.checkCalculableContext()) {
            fillContentsRandom(childNode);
            return;
        }
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        String subString = GlobalRandom.getInstance().nextSubstring(childList.get(0).context);
        double prob = GlobalRandom.getInstance().nextDouble();

        if(prob < 0.1)
            subString = "";
        else if(prob < 0.2)
            subString = XMLDatatype.STRING.getValueHandler().mutateValue(subString);
        childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, subString));
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        String subString = XMLDatatype.STRING.getValueHandler().getValue(false);
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.1)
            subString = "";
        childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, subString));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.STRING;
    }

    @Override
    public SubstringBeforeFunctionNode newInstance() {
        return new SubstringBeforeFunctionNode();
    }
}
