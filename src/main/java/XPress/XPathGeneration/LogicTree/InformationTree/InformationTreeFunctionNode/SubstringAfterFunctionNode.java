package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.GlobalRandom;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class SubstringAfterFunctionNode extends InformationTreeFunctionNode {
    private String internalStr = null;
    public SubstringAfterFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLString.getInstance();
        functionExpr = "substring-after";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String subString = GlobalRandom.getInstance().nextSubstring(childList.get(0).getContext().context);
        internalStr = subString;
        fillContentParametersWithStr(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String subString = XMLString.getInstance().getValueHandler().getValue(false);
        internalStr = subString;
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
    public SubstringAfterFunctionNode newInstance() {
        return new SubstringAfterFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
    }
 }
