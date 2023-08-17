package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.GlobalRandom;
import XPress.Pair;
import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
@FunctionV1
public class ContainsFunctionNode extends InformationTreeFunctionNode {
    public ContainsFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        functionExpr = "contains";
    }

    @Override
    public ContainsFunctionNode newInstance() {
        return new ContainsFunctionNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String childString = childList.get(0).getContext().context;
        if(childString.length() == 0) {
            fillContentParametersRandom(childNode);
            return;
        }
        Pair subStringInterval = GlobalRandom.getInstance().nextInterval(childString.length());
        int l = subStringInterval.x, r = subStringInterval.y;
        String subString;
        double prob = GlobalRandom.getInstance().nextDouble();
        subString = childString.substring(l, r);
        if(prob < 0.2)
            subString = XMLDatatype.STRING.getValueHandler().mutateValue(subString);
        InformationTreeConstantNode constantNode = new InformationTreeConstantNode
                (XMLDatatype.STRING, subString);
        childList.add(constantNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String subString;
        subString = XMLDatatype.STRING.getValueHandler().getValue();
        InformationTreeConstantNode constantNode = new InformationTreeConstantNode
                (XMLDatatype.STRING, subString);
        childList.add(constantNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }
}
