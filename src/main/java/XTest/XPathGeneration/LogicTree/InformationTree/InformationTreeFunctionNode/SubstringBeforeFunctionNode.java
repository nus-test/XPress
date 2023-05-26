package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public class SubstringBeforeFunctionNode extends InformationTreeFunctionNode {
    public SubstringBeforeFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "substring-before";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String subString = GlobalRandom.getInstance().nextSubstring(childList.get(0).getContext().context);
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.1)
            subString = "";
        else if(prob < 0.2)
            subString = XMLDatatype.STRING.getValueHandler().mutateValue(subString);
        childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, subString));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
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
