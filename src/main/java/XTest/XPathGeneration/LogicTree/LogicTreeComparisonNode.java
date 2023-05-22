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
        contextInfo.XPathPrefix = nodeA.contextInfo.XPathPrefix;
    }

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode) {
        if(returnConstant && context != null) return context;
        String returnString = childList.get(0).getXPathExpression(returnConstant, this)
                + funcExpr + childList.get(1).getXPathExpression(returnConstant, this);
        if(parentNode instanceof LogicTreeComparisonNode) {
            returnString = "(" + returnString + ")";
        }
        return returnString;
    }
}
