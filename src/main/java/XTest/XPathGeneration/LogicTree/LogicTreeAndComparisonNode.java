package XTest.XPathGeneration.LogicTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.TestException.UnexpectedExceptionThrownException;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class LogicTreeAndComparisonNode extends LogicTreeComparisonNode {
    @Override
    public LogicTreeComparisonNode modifyToContainStarredNode(MainExecutor mainExecutor, int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        childList.set(0, childList.get(0).modifyToContainStarredNode(mainExecutor, starredNodeId));
        childList.set(1, childList.get(1).modifyToContainStarredNode(mainExecutor, starredNodeId));
        return this;
    }
}
