package XTest.XPathGeneration.LogicTree.InfomationTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeNotFunctionNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class InformationTreeNode extends LogicTreeNode {

    public XMLDatatypeComplexRecorder dataTypeRecorder = new XMLDatatypeComplexRecorder();

    /**
     * If is calculable, contains the real value of evaluated context for the starred node
     */
    String context;

    /**
     * If is sequence type, contains the length of sequence for the starred node
     */
    int length;

    /**
     * If within subtree there is no context node or an ancestor node of the context node is
     * with calculated context value is set to true.
     */
    public boolean containsContextConstant = false;

    /**
     * Starred node id which is recorded in the unique context node.
     */
    protected int starredNodeId = -1;
    /**
     * If set to true unique context node refers to the starred node in context of XPath prefix.
     * Else refers to a derived sequence from the starred node with itself as the context.
     */
    protected boolean selfContext = true;

    public List<InformationTreeNode> childList = new ArrayList<>();

    /**
     *
     * @return The calculation string which could compute the correct result for the starred node of
     * current information tree. For nodes which returns a sequence calculates the sequence length instead of
     * actual value.
     */
    String getCalculationString() throws DebugErrorException {
        String calculationString = "";
        if(containsContextConstant) calculationString = getXPathExpression(true);
        else if(!selfContext) {
            calculationString = "//*[id = \"" + starredNodeId + "\"]/" + getXPathExpression();
        } else if(this instanceof InformationTreeDirectContentFunctionNode)
            calculationString = ((InformationTreeDirectContentFunctionNode) this).getCurrentLevelCalculationString();
        else throw new DebugErrorException();
        if(dataTypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            calculationString = "count(" + calculationString + ")";
        return calculationString;
    }

    /**
     *
     * @return Context of subtree represented by current node.
     */
    String getContext() {
        return context;
    }

    boolean checkIfContainsStarredNode(MainExecutor mainExecutor) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        return checkIfContainsStarredNode(mainExecutor, starredNodeId);
    }

    boolean checkIfContainsStarredNode(MainExecutor mainExecutor, int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        String expr = getXPathExpression();
        List<Integer> resultList = mainExecutor.executeSingleProcessorGetIdList(expr);
        return resultList.contains(starredNodeId);
    }

    @Override
    public InformationTreeNode modifyToContainStarredNode(MainExecutor mainExecutor, int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        boolean contains = checkIfContainsStarredNode(mainExecutor, starredNodeId);
        if(contains) return this;
        InformationTreeNotFunctionNode newRoot = new InformationTreeNotFunctionNode();
        newRoot.fillContents(this);
        return newRoot;
    }
}
