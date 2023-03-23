package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class TranslateFunctionNode extends PredicateTreeFunctionNode{
    TranslateFunctionNode() {
        datatype = XMLDatatype.STRING;
        XPathExpr = "translate";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        int length = GlobalRandom.getInstance().nextInt(0, 21);
        String mapStr = XMLDatatype.STRING.getValueHandler().getValue();
        String transStr = XMLDatatype.STRING.getValueHandler().getValue();
        PredicateTreeConstantNode mapNode = new PredicateTreeConstantNode(
                XMLDatatype.STRING, mapStr
        );
        PredicateTreeConstantNode transNode = new PredicateTreeConstantNode(
                XMLDatatype.STRING, transStr
        );
        childList.add(mapNode);
        childList.add(transNode);
    }

    @Override
    public PredicateTreeFunctionNode newInstance() {
        return new TranslateFunctionNode();
    }

    @Override
    public String calculationString() { return XPathExpr + "(" + getContentListOfString(childList) + ")"; }
}
