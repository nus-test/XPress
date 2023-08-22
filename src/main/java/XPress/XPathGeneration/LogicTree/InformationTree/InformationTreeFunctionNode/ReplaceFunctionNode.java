package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.GlobalRandom;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

//@FunctionV3
public class ReplaceFunctionNode extends InformationTreeFunctionNode {
    private String internalStr = null;

    public ReplaceFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLString.getInstance();
        functionExpr = "replace";
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
        if(internalStr.length() == 0) internalStr = "a";
        childList.add(new InformationTreeConstantNode(XMLString.getInstance(), internalStr.replaceAll("[{}()*.]", "e").replace('\\', 'q')));
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.6) {
            childList.add(new InformationTreeConstantNode(XMLString.getInstance(),
                    XMLString.getInstance().getValueHandler().getValue(false).replaceAll("[${}()*.]", "c")
                            .replace('\\', 'z')));
        } else {
            if(prob < 0.9) childList.add(new InformationTreeConstantNode(XMLString.getInstance(), "$1"));
            else {
                int num = GlobalRandom.getInstance().nextInt(10) + 1;
                childList.add(new InformationTreeConstantNode(XMLString.getInstance(), "$" + num));
            }
        }
        internalStr = null;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
    }


    @Override
    public ReplaceFunctionNode newInstance() {
        return new ReplaceFunctionNode();
    }
}
