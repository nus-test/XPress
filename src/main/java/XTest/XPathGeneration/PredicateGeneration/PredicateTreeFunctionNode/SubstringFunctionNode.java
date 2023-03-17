package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.Pair;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class SubstringFunctionNode extends PredicateTreeFunctionNode {

    SubstringFunctionNode() {
        this.datatype = XMLDatatype.STRING;
        this.XPathExpr = "substring";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        double prob = GlobalRandom.getInstance().nextDouble();
        Pair interval = GlobalRandom.getInstance().nextInterval(inputNode.dataContent.length());
        PredicateTreeConstantNode constantNodeStart = new PredicateTreeConstantNode
                (XMLDatatype.INTEGER, Integer.toString(interval.x));
        PredicateTreeConstantNode constantNodeLength = new PredicateTreeConstantNode
                (XMLDatatype.INTEGER, Integer.toString(interval.y - interval.x + 1));
        childList.add(constantNodeStart);
        if(prob < 0.5)
            childList.add(constantNodeLength);
    }

    @Override
    public PredicateTreeFunctionNode newInstance() {
        return new SubstringFunctionNode();
    }
}
