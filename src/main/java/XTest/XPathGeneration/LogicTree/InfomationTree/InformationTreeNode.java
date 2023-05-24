package XTest.XPathGeneration.LogicTree.InfomationTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.NotFunctionNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class InformationTreeNode extends LogicTreeNode {
    /**
     * Supplementary context only works for sequence of nodes, includes id of one random node in sequence
     */
    public String supplementaryContext = null;

    /**
     * If is sequence type, contains the length of sequence for the starred node
     */
    int length;

    public List<InformationTreeNode> childList = new ArrayList<>();

    protected InformationTreeNode() {
        contextInfo = new InformationTreeContextInfo();
    }

    public void setContextInfo(InformationTreeContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }

    public void setContextInfo(MainExecutor mainExecutor, String XPathPrefix, int starredNodeId,
                               boolean containsContext, boolean constantExpr, boolean selfContext) {
        this.contextInfo = new InformationTreeContextInfo(mainExecutor, XPathPrefix, starredNodeId,
                containsContext, constantExpr, selfContext);
    }

    public InformationTreeNode(MainExecutor mainExecutor, String XPathPrefix, int starredNodeId,
                                      boolean containsContext, boolean constantExpr, boolean selfContext) {
        contextInfo = new InformationTreeContextInfo(mainExecutor, XPathPrefix, starredNodeId,
                containsContext, constantExpr, selfContext);
    }


    @Override
    public InformationTreeContextInfo getContextInfo() {
        return (InformationTreeContextInfo) contextInfo;
    }

    public String getXPathExpressionCheck(boolean returnConstant) {
        if(!getContextInfo().containsContext && checkCalculableContext())
            return XMLDatatype.wrapExpression(context, datatypeRecorder.xmlDatatype);
        if(returnConstant && checkCalculableContext())
            return XMLDatatype.wrapExpression(context, datatypeRecorder.xmlDatatype);
        if(!returnConstant && XPathExpr != null)
            return XPathExpr;
        return null;
    }

    /**
     *
     * @return The calculation string which could compute the correct result for the starred node of
     * current information tree.
     */
    public String getCalculationString() throws DebugErrorException {
        String calculationString = "";
        if(getContextInfo().constantExpr) calculationString = getXPathExpression(true);
        else if(!getContextInfo().selfContext) {
            calculationString = "//*[@id=\"" + getContextInfo().starredNodeId + "\"]/" + getXPathExpression(true);
        } else if(childList.get(0).datatypeRecorder.xmlDatatype == XMLDatatype.NODE)
            calculationString = getCurrentLevelCalculationString();
        else calculationString = getXPathExpression(true);
        return calculationString;
    }

    public String getCurrentLevelCalculationString() {
        return null;
    }

    /**
     *
     * @return Context of subtree represented by current node.
     */
    String getContext() {
        return context;
    }

    boolean checkIfContainsStarredNode() throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        return checkIfContainsStarredNode(getContextInfo().starredNodeId);
    }

    @Override
    public InformationTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        NotFunctionNode newRoot = new NotFunctionNode();
        newRoot.fillContents(this);
        return newRoot;
    }

    /**
     * Every ancestor in the information tree of the unique context node should contain the necessary information
     * about the unique context node.
     * @param childNode
     */
    public void inheritContextChildInfo(InformationTreeNode childNode) {
        contextInfo = new InformationTreeContextInfo(childNode);
    }

    public void calculateInfo() throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        String calculationString = getCalculationString();
        if(calculationString != null) {
            if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
                context = contextInfo.mainExecutor.executeSingleProcessor("count(" + calculationString + ")");
            }
            else context = contextInfo.mainExecutor.executeSingleProcessor(calculationString);
            if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE && datatypeRecorder.subDatatype == XMLDatatype.NODE) {
                Integer size = Integer.parseInt(context);
                Integer randomId = GlobalRandom.getInstance().nextInt(size) + 1;
                supplementaryContext = contextInfo.mainExecutor.executeSingleProcessor(calculationString + '[' +
                        randomId + "]/@id");
            }
        }
        setCalculableContextFlag();
    }

    public void setCalculableContextFlag() {
        boolean flag = true;
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) flag = false;
        if(datatypeRecorder.subDatatype == XMLDatatype.NODE) flag = false;
        getContextInfo().constantExpr = flag;
    }

    public Boolean checkCalculableContext() {
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) return false;
        if(datatypeRecorder.xmlDatatype == XMLDatatype.NODE) return false;
        return context != null;
    }
}
