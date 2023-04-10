package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.Pair;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class SubstringBeforeFunctionNode extends PredicateTreeFunctionNode {
    SubstringBeforeFunctionNode() {
        this.datatype = XMLDatatype.STRING;
        XPathExpr = "substring-before";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        String childString = childList.get(0).dataContent;
        Pair subStringInterval = GlobalRandom.getInstance().nextInterval(childString.length());
        int l = subStringInterval.x, r = subStringInterval.y;
        String subString;
        subString = childString.substring(l, r);
        double prob = GlobalRandom.getInstance().nextDouble();

        if(prob < 0.1)
            subString = "";
        else if(prob < 0.2)
            subString = XMLDatatype.STRING.getValueHandler().mutateValue(subString);
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode
                (XMLDatatype.STRING, subString);
        childList.add(constantNode);
    }

    @Override
    public SubstringBeforeFunctionNode newInstance() {
        return new SubstringBeforeFunctionNode();
    }

    @Override
    public String calculationString() { return XPathExpr + "(\"" + childList.get(0).dataContent + "\", \"" + childList.get(1).dataContent + "\")"; }
}
