package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.GlobalRandom;
import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class SubstringBeforeFunctionNode extends InformationTreeFunctionNode {
    private String internalStr = null;
    public SubstringBeforeFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "substring-before";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String subString = GlobalRandom.getInstance().nextSubstring(childList.get(0).getContext().context);
        internalStr = subString;
        fillContentParametersWithStr(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String subString = XMLDatatype.STRING.getValueHandler().getValue(false);
        internalStr = subString;
        fillContentParametersWithStr(childNode);
    }

    private void fillContentParametersWithStr(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.1)
            internalStr = "";
        childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, internalStr));
        internalStr = null;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }

    @Override
    public SubstringBeforeFunctionNode newInstance() {
        return new SubstringBeforeFunctionNode();
    }
}
