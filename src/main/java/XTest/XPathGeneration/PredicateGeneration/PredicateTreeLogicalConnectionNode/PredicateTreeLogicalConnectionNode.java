package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode;

import XTest.CommonUtils;
import XTest.GlobalRandom;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public abstract class PredicateTreeLogicalConnectionNode extends PredicateTreeNode {
    static List<PredicateTreeLogicalConnectionNode> logicalConnectionNodeList = new ArrayList<>();

    static {
        logicalConnectionNodeList.add(new AndConnectionNode());
        logicalConnectionNodeList.add(new NotConnectionNode());
        logicalConnectionNodeList.add(new OrConnectionNode());
    }

    public static PredicateTreeLogicalConnectionNode getRandomLogicalConnectionNode() {
        System.out.println("length: " + logicalConnectionNodeList.size());
        return GlobalRandom.getInstance().getRandomFromList(logicalConnectionNodeList).newInstance();
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
        if(child instanceof PredicateTreeLogicalConnectionNode)
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
