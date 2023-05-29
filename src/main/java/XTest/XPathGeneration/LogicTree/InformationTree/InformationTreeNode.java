package XTest.XPathGeneration.LogicTree.InformationTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.NotFunctionNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class InformationTreeNode extends LogicTreeNode {

    public List<InformationTreeNode> childList = new ArrayList<>();

    protected InformationTreeNode() {
        contextInfo = new InformationTreeContextInfo();
        context = new InformationTreeContext();
    }

    public InformationTreeContext getContext() {
        return (InformationTreeContext) context;
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

    public String getXPathExpressionCheck(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        if(calculateString)
            return getCalculationString(parentNode, true);
        if(!getContextInfo().containsContext && checkValidCalculableContext())
            return XMLDatatype.wrapExpression(getContext().context, datatypeRecorder.xmlDatatype);
        if(returnConstant && checkValidCalculableContext())
            return XMLDatatype.wrapExpression(getContext().context, datatypeRecorder.xmlDatatype);
        if(!returnConstant && XPathExpr != null)
            return XPathExpr;
        return null;
    }

    boolean checkIfContainsStarredNode() throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
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
        if(calculationString.equals("((sort(sort(head(reverse(sort(reverse(//*[@id=\"8\"]/V1/has-children())))))))[1]) = 1()")) {
            System.out.println("PPPPPPPPPPPP -------------------");
            System.out.println(this.getClass());
            System.out.println(this.datatypeRecorder.getActualDatatype());
            System.out.println(this.childList.get(1).context.context);
            System.out.println(this.childList.get(1).datatypeRecorder.xmlDatatype);
            System.out.println("childList size: " + childList.size());
            System.out.println(childList.get(0) instanceof InformationTreeContextNode);
            System.out.println(((InformationTreeContextNode) childList.get(0)).dummyContext);
            System.out.println(((InformationTreeContextNode) childList.get(0)).context.context);
            System.out.println(this.childList.get(0).datatypeRecorder.xmlDatatype);
            System.out.println(((InformationTreeContextNode) childList.get(0)).dummyCalculateString);
//            System.out.println(((InformationTreeContextNode) childList.get(0)).getCalculationString());
//            System.out.println(((InformationTreeContextNode) childList.get(0)).checkValidContext());
            System.out.println("PPPPPPPPPPPP -------------------");
        }
        if(datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            getContext().context = contextInfo.mainExecutor.executeSingleProcessor(calculationString + "/@id cast as xs:integer");
        }
        else if(calculationString != null) {
            if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
                getContext().context = contextInfo.mainExecutor.executeSingleProcessor("count(" + calculationString + ")");
            }
            else getContext().context = contextInfo.mainExecutor.executeSingleProcessor(calculationString);
            if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE && datatypeRecorder.subDatatype == XMLDatatype.NODE) {
                Integer size = Integer.parseInt(getContext().context);
                if(size != 0) {
                    Integer randomId = GlobalRandom.getInstance().nextInt(size) + 1;
                    getContext().supplementaryContext = contextInfo.mainExecutor.executeSingleProcessor("(" + calculationString + ")[" +
                            randomId + "]/@id cast as xs:integer");
                }
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

    /**
     * Check if current node has valid context in form of constants;
     * @return
     */
    public Boolean checkValidCalculableContext() {
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) return false;
        if(datatypeRecorder.xmlDatatype == XMLDatatype.NODE) return false;
        return getContext().context != null;
    }

    /**
     * Check if current node has valid context in any form (Including node ids)
     * @return
     */
    public Boolean checkValidContext() {
        return getContext().context != null;
    }

    /**
     *
     * @return Calculation XPathExpression which executed produces the node set that current node belongs to after last selection.
     * Should only be called on tree nodes which have XMLDatatype NODE(single node).
     * @throws DebugErrorException
     */
    public String getContextCalculationString() throws DebugErrorException {
        if(getChildList().size() != 0)
            return getChildList().get(0).getCalculationString();
        // Else: this is leaf context node
        // Case 1: self context - return XPathPrefix
        // Case 2: is not self context - not useful any way
        return contextInfo.XPathPrefix;
    }

    @Override
    public List<InformationTreeNode> getChildList() {
        return childList;
    }
}
