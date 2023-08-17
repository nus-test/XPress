package XPress.XPathGeneration.LogicTree;

import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;

import java.io.IOException;
import java.sql.SQLException;

public class LogicTreeAndComparisonNode extends LogicTreeComparisonNode {
    LogicTreeAndComparisonNode() {
        funcExpr = "and";
    }

    @Override
    public LogicTreeAndComparisonNode newInstance() {
        return new LogicTreeAndComparisonNode();
    }

    @Override
    public LogicTreeComparisonNode modifyToContainStarredNode(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        childList.set(0, childList.get(0).modifyToContainStarredNodeWithCheck(starredNodeId));
        childList.set(1, childList.get(1).modifyToContainStarredNodeWithCheck(starredNodeId));
        return this;
    }
}
