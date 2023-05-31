package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class SubstringAfterFunctionNode extends InformationTreeFunctionNode {
    public SubstringAfterFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "substring-after";
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
    public SubstringAfterFunctionNode newInstance() {
        return new SubstringAfterFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }
 }
