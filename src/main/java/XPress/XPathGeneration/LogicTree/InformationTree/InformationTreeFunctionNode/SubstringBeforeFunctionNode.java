package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.GlobalRandom;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class SubstringBeforeFunctionNode extends InformationTreeFunctionNode {
    private String internalStr = null;
    public SubstringBeforeFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLString.getInstance();
        functionExpr = "substring-before";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        internalStr = GlobalRandom.getInstance().nextSubstring(childList.get(0).getContext().context);
        fillContentParametersWithStr(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        internalStr = XMLString.getInstance().getValueHandler().getValue(false);
        fillContentParametersWithStr(childNode);
    }

    private void fillContentParametersWithStr(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.1)
            internalStr = "";
        childList.add(new InformationTreeConstantNode(XMLString.getInstance(), internalStr));
        internalStr = null;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
    }

    @Override
    public SubstringBeforeFunctionNode newInstance() {
        return new SubstringBeforeFunctionNode();
    }
}
