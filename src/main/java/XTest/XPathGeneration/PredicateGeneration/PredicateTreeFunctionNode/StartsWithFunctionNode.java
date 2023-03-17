package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class StartsWithFunctionNode extends PredicateTreeFunctionNode{
    StartsWithFunctionNode() {
        datatype = XMLDatatype.STRING;
        XPathExpr = "starts-with";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        this.childList.add(inputNode);
        double prob = GlobalRandom.getInstance().nextDouble();
        int endIndex = GlobalRandom.getInstance().nextInt(inputNode.dataContent.length()) + 1;
        String endStr = inputNode.dataContent.substring(0, endIndex);
        if(prob < 0.2) {
            endStr = XMLDatatype.STRING.getValueHandler().mutateValue(endStr);
        }
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode(XMLDatatype.STRING, endStr);
        this.childList.add(constantNode);
    }

    @Override
    public StartsWithFunctionNode newInstance() {
        return new StartsWithFunctionNode();
    }
}
