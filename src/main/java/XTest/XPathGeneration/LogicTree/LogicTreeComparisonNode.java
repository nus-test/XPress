package XTest.XPathGeneration.LogicTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.TestException.DebugErrorException;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicTreeComparisonNode extends LogicTreeNode {
    List<LogicTreeNode> childList = new ArrayList<>();
    static List<LogicTreeComparisonNode> candidateList = new ArrayList<>();
    String funcExpr;

    {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
    }

    static {
        candidateList.add(new LogicTreeAndComparisonNode());
        candidateList.add(new LogicTreeOrComparisonNode());
    }

    @Override
    abstract public LogicTreeComparisonNode newInstance();

    public static LogicTreeComparisonNode getRandomCandidate() {
        return GlobalRandom.getInstance().getRandomFromList(candidateList);
    }

    public void fillContents(LogicTreeNode nodeA, LogicTreeNode nodeB) {
        childList.add(nodeA);
        childList.add(nodeB);
        contextInfo = new LogicTreeContextInfo(nodeA.contextInfo);
    }

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        if(returnConstant && getContext().context != null) return getContext().context;
        String returnString = childList.get(0).getXPathExpression(returnConstant, this, calculateString)
                + " " + funcExpr + " " + childList.get(1).getXPathExpression(returnConstant, this, calculateString);
        if(parentNode instanceof LogicTreeComparisonNode) {
            returnString = "(" + returnString + ")";
        }
        return returnString;
    }

    @Override
    public List<LogicTreeNode> getChildList() {
        return childList;
    }
}
