package XPress.XPathGeneration.LogicTree;

import XPress.GlobalRandom;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;

import java.io.IOException;
import java.sql.SQLException;

public class LogicTreeOrComparisonNode extends LogicTreeComparisonNode{
    LogicTreeOrComparisonNode() {
        funcExpr = "or";
    }

    @Override
    public LogicTreeOrComparisonNode newInstance() {
        return new LogicTreeOrComparisonNode();
    }

    @Override
    public LogicTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        int id = GlobalRandom.getInstance().nextInt(2);
        childList.set(id, childList.get(id).modifyToContainStarredNodeWithCheck(starredNodeId));
        return this;
    }
}
