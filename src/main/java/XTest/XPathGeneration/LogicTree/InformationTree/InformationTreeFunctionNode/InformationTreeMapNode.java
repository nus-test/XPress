package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeGenerator;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class InformationTreeMapNode extends BinaryOperatorFunctionNode {

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        // TODO: Check the first child type and decide whether needs to be wrapped by "()"

        String builder = childList.get(0).getXPathExpression(returnConstant) + "! ";
        if(childList.size() > 2)
            builder += "(";
        boolean start = true;
        for(int i = 1; i < childList.size(); i ++) {
            if(!start) builder += ",";
            builder += ((InformationTreeFunctionNode) childList.get(i)).getXPathExpression(false, null, false);
            start = false;
        }
        if(childList.size() > 2)
            builder += ")";
        return builder;
    }

    @Override
    public InformationTreeMapNode newInstance() {
        return new InformationTreeMapNode();
    }

    @Override
    public void fillContentParameters(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        InformationTreeNode dummyChildNode = InformationTreeFunctionNodeManager.getInstance().getDummyChildNode(childNode);
        Integer functionCnt = fillContentParameterFunctions(childNode);
        for(int i = 1; i <= functionCnt; i ++) {
            ((InformationTreeFunctionNode) childList.get(i)).fillContents(dummyChildNode);
        }
        inferResultRecorder(functionCnt);
    }

    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        InformationTreeNode dummyChildNode = InformationTreeFunctionNodeManager.getInstance().getDummyChildNode(childNode);
        Integer functionCnt = fillContentParameterFunctions(childNode);
        for(int i = 1; i <= functionCnt; i ++) {
            ((InformationTreeFunctionNode) childList.get(i)).fillContentsRandom(dummyChildNode);
        }
        inferResultRecorder(functionCnt);
    }

    private Integer fillContentParameterFunctions(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        int functionCnt = 1;
        double prob = GlobalRandom.getInstance().nextDouble();
        InformationTreeGenerator informationTreeGenerator = new InformationTreeGenerator(contextInfo.mainExecutor);
        if(prob < 0.2) {
            if(prob < 0.1) {
                functionCnt = 2;
            }
            else {
                functionCnt = GlobalRandom.getInstance().nextInt(3) + 1;
            }
        }
        for(int i = 0; i < functionCnt; i ++) {
            InformationTreeNode treeNode = informationTreeGenerator.buildInformationTree(childNode, GlobalRandom.getInstance().nextInt(3));
            childList.add(treeNode);
        }
        return functionCnt;
    }

    private void inferResultRecorder(Integer functionCnt) {
        XMLDatatypeComplexRecorder recorder = new XMLDatatypeComplexRecorder();
        if(functionCnt > 1) {
            recorder.xmlDatatype = XMLDatatype.SEQUENCE;
        }
        XMLDatatype actualType = null;
        for(int i = 1; i <= functionCnt; i ++) {
            if(((InformationTreeFunctionNode) childList.get(i)).datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
                recorder.xmlDatatype = XMLDatatype.SEQUENCE;
            if(actualType != XMLDatatype.MIXED) {
                if (actualType == null) {
                    actualType = ((InformationTreeFunctionNode) childList.get(i)).datatypeRecorder.getActualDatatype();
                } else if (actualType != ((InformationTreeFunctionNode) childList.get(i)).datatypeRecorder.getActualDatatype())
                    actualType = XMLDatatype.MIXED;
            }
        }
        if(recorder.xmlDatatype == XMLDatatype.SEQUENCE)
            recorder.subDatatype = actualType;
        else recorder.xmlDatatype = actualType;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return true;
    }
}
