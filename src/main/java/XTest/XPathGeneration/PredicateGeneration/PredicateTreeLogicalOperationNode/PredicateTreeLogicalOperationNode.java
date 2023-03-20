package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLComparable;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;
import XTest.XPathGeneration.PredicateGeneration.UnaryPredicateTreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PredicateTreeLogicalOperationNode extends PredicateTreeNode {

    static List<PredicateTreeLogicalOperationNode> comparativeOperationNodeList = new ArrayList<>();
    static Map<Class, PredicateTreeLogicalOperationNode> oppositeNodeMap = new HashMap();


    PredicateTreeLogicalOperationNode() {
        this.datatype = XMLDatatype.BOOLEAN;
    }

    static {
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new GreaterThanOperationNode());
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new GreaterOrEqualOperationNode());
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new LessThenOperationNode());
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new LessOrEqualOperationNode());
        addOppositeNodePairToMap(new GreaterThanOperationNode(), new LessOrEqualOperationNode());
        addOppositeNodePairToMap(new GreaterOrEqualOperationNode(), new LessThenOperationNode());
        addOppositeNodePairToMap(new EqualOperationNode(), new NotEqualOperationNode());
    }

    static void addOppositeNodePairToMap(PredicateTreeLogicalOperationNode node1, PredicateTreeLogicalOperationNode node2) {
        oppositeNodeMap.put(node1.getClass(), node2);
        oppositeNodeMap.put(node2.getClass(), node1);
    }

    public static PredicateTreeLogicalOperationNode getRandomLogicalOperationNode(XMLDatatype datatype) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if (datatype == XMLDatatype.DOUBLE || (datatype.getValueHandler() instanceof XMLComparable && prob < 0.7))
            return GlobalRandom.getInstance().getRandomFromList(comparativeOperationNodeList).newInstance();
        else return new EqualOperationNode();
    }

    abstract public PredicateTreeLogicalOperationNode newInstance();

    public PredicateTreeLogicalOperationNode getOppositeOperationNode() {
        PredicateTreeLogicalOperationNode oppositeNode = oppositeNodeMap.get(this.getClass()).newInstance();
        copyAllInfo(oppositeNode);
        return oppositeNode;
    }

    public void copyAllInfo(PredicateTreeLogicalOperationNode newNode) {
        newNode.childList = this.childList;
    }

    public void join(PredicateTreeNode leftChild, PredicateTreeNode rightChild) {
        childList.add(leftChild);
        childList.add(rightChild);
    }

    public String toString() {
        String leftExpr = wrapExpr(childList.get(0));
        String rightExpr = wrapExpr(childList.get(1));
        return leftExpr + " " + this.XPathExpr + " " + rightExpr;
    }

    String wrapExpr(PredicateTreeNode child) {
        String expr = child.toString();
        if(!(child instanceof UnaryPredicateTreeNode))
            expr = "(" + expr + ")";
        return expr;
    }
}
