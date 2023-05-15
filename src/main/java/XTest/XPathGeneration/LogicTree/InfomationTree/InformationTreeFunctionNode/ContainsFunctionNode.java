package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.Pair;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class ContainsFunctionNode extends InformationTreeFunctionNode {
    ContainsFunctionNode() {
        this.dataTypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        functionExpr = "contains";
    }

    @Override
    public ContainsFunctionNode newInstance() {
        return new ContainsFunctionNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(childList.get(0).context == null) {
            fillContentsRandom(childNode);
            return;
        }
        childList.add(childNode);
        String childString = childList.get(0).context;
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
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        String subString;
        subString = XMLDatatype.STRING.getValueHandler().getValue();
        InformationTreeConstantNode constantNode = new InformationTreeConstantNode
                (XMLDatatype.STRING, subString);
        childList.add(constantNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        // TODO: Current observation all could be transformed into type of string, but not verified.
        return true;
    }
}
