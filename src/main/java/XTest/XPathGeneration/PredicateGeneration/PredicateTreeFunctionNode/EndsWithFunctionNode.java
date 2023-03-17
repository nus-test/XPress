package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class EndsWithFunctionNode extends PredicateTreeFunctionNode {
    EndsWithFunctionNode() {
        datatype = XMLDatatype.BOOLEAN;
        XPathExpr = "ends-with";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        this.childList.add(inputNode);
        double prob = GlobalRandom.getInstance().nextDouble();
        int startIndex = GlobalRandom.getInstance().nextInt(inputNode.dataContent.length());
        String endStr = inputNode.dataContent.substring(startIndex);
        if(prob < 0.2) {
            endStr = XMLDatatype.STRING.getValueHandler().mutateValue(endStr);
        }
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode(XMLDatatype.STRING, endStr);
        this.childList.add(constantNode);
    }

    @Override
    public EndsWithFunctionNode newInstance() {
        return new EndsWithFunctionNode();
    }
}
