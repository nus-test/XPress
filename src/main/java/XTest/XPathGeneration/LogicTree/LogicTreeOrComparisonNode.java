package XTest.XPathGeneration.LogicTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.TestException.UnexpectedExceptionThrownException;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class LogicTreeOrComparisonNode extends LogicTreeComparisonNode{
    LogicTreeOrComparisonNode() {
        funcExpr = "or";
    }

    @Override
    public LogicTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        int id = GlobalRandom.getInstance().nextInt(2);
        childList.set(id, childList.get(id).modifyToContainStarredNode(starredNodeId));
        return this;
    }
}
