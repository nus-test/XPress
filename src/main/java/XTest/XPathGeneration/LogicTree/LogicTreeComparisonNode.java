package XTest.XPathGeneration.LogicTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicTreeComparisonNode extends LogicTreeNode {
    List<LogicTreeNode> childList = new ArrayList<>();
    static List<LogicTreeComparisonNode> candidateList = new ArrayList<>();
    String funcExpr;

    {
        dataTypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
    }

    static {
        candidateList.add(new LogicTreeAndComparisonNode());
        candidateList.add(new LogicTreeOrComparisonNode());
    }

    public static LogicTreeComparisonNode getRandomCandidate() {
        return GlobalRandom.getInstance().getRandomFromList(candidateList);
    }

    public void fillContents(LogicTreeNode nodeA, LogicTreeNode nodeB) {
        childList.add(nodeA);
        childList.add(nodeB);
    }

    public static String wrapComparisonExpr(LogicTreeNode node, boolean returnConstant) {
        if(node instanceof LogicTreeComparisonNode)
            return "(" + node.getXPathExpression(returnConstant) + ")";
        return node.getXPathExpression(returnConstant);
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        if(returnConstant && context != null) return context;
        return wrapComparisonExpr(childList.get(0), returnConstant)
                + funcExpr + wrapComparisonExpr(childList.get(1), returnConstant);
    }
}
