package XTest.XPathGeneration.LogicTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

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
    public LogicTreeComparisonNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        childList.set(0, childList.get(0).modifyToContainStarredNodeWithCheck(starredNodeId));
        childList.set(1, childList.get(1).modifyToContainStarredNodeWithCheck(starredNodeId));
        return this;
    }
}
