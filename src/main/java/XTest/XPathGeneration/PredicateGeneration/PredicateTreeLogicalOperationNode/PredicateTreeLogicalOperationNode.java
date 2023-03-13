package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLComparable;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import java.util.ArrayList;
import java.util.List;

public abstract class PredicateTreeLogicalOperationNode extends PredicateTreeNode {

    static List<PredicateTreeLogicalOperationNode> comparativeOperationNodeList = new ArrayList<>();

    static {
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new GreaterThanOperationNode());
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new GreaterOrEqualOperationNode());
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new LessThenOperationNode());
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new LessOrEqualOperationNode());
    }

    public static PredicateTreeLogicalOperationNode getRandomLogicalOperationNode(XMLDatatype datatype) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(datatype.getValueHandler() instanceof XMLComparable && prob < 0.7)
            return GlobalRandom.getInstance().getRandomFromList(comparativeOperationNodeList).newInstance();
        else return new EqualOperationNode();
    }

    abstract public PredicateTreeLogicalOperationNode newInstance();

    public void join(PredicateTreeNode leftChild, PredicateTreeNode rightChild) {
        childList.add(leftChild);
        childList.add(rightChild);
    }
}
