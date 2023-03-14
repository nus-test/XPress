package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode;

import XTest.GlobalRandom;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;
import XTest.XPathGeneration.PredicateGeneration.UnaryPredicateTreeNode;

import java.util.ArrayList;
import java.util.List;

public abstract class PredicateTreeLogicalConnectionNode extends PredicateTreeNode {
    static List<PredicateTreeLogicalConnectionNode> binaryLogicalConnectionNodeList = new ArrayList<>();
    static List<PredicateTreeLogicalConnectionNode> unaryLogicalConnectionNodeList = new ArrayList<>();

    static {
        binaryLogicalConnectionNodeList.add(new AndConnectionNode());
        binaryLogicalConnectionNodeList.add(new OrConnectionNode());
        unaryLogicalConnectionNodeList.add(new NotConnectionNode());
    }

    public static PredicateTreeLogicalConnectionNode getRandomBinaryLogicalConnectionNode() {
        return GlobalRandom.getInstance().getRandomFromList(binaryLogicalConnectionNodeList).newInstance();
    }

    public static PredicateTreeLogicalConnectionNode getRandomUnaryLogicalConnectionNode() {
        return GlobalRandom.getInstance().getRandomFromList(unaryLogicalConnectionNodeList).newInstance();
    }

    public void join(PredicateTreeNode leftChild, PredicateTreeNode rightChild) {
        childList.add(leftChild);
        childList.add(rightChild);
    }

    public void addChild(PredicateTreeNode child) {
        childList.add(child);
    }

    public String getChildExpr(PredicateTreeNode child) {
        String expr = child.toString();
        if(!(child instanceof UnaryPredicateTreeNode))
            expr = "(" + expr + ")";
        return expr;
    }

    public String toString() {
        PredicateTreeNode leftChild = childList.get(0);
        PredicateTreeNode rightChild = childList.get(1);
        String leftExpr = getChildExpr(leftChild);
        String rightExpr = getChildExpr(rightChild);
        return leftExpr + " " + this.XPathExpr + " " + rightExpr;
    }

    abstract public PredicateTreeLogicalConnectionNode newInstance();
}
