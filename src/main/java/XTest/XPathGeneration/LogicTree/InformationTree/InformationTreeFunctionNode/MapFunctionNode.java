package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.XMLAtomic;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode.AttributeFunctionNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeGenerator;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

@FunctionV3
public class MapFunctionNode extends BinaryOperatorFunctionNode {
    public boolean mixAttrFlag = false;

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        // TODO: Check the first child type and decide whether needs to be wrapped by "()"
        String returnString = getXPathExpressionCheck(returnConstant, parentNode, calculateString);
        if(returnString != null) return binaryWrap(returnString, parentNode);
        returnString = childList.get(0).getXPathExpression(returnConstant, this, calculateString) + " ! ";
        if(childList.size() > 2)
            returnString += "(";
        boolean start = true;
        for(int i = 1; i < childList.size(); i ++) {
            if(!start) returnString += ",";
            returnString += childList.get(i).getXPathExpression(false,
                    childList.size() > 2 ? null : this, false);
            start = false;
        }
        if(childList.size() > 2)
            returnString += ")";
        cacheXPathExpression(returnString, returnConstant, calculateString);
        return binaryWrap(returnString, parentNode, calculateString);
    }

    public String binaryWrap(String resultString, LogicTreeNode parentNode, boolean calculateString) {
        if(calculateString && datatypeRecorder.xmlDatatype.getValueHandler() instanceof XMLAtomic)
            return "((" + resultString + ") cast as " + datatypeRecorder.xmlDatatype.getValueHandler().officialTypeName + ")";
        if(parentNode != null) {
            return super.binaryWrap(resultString, parentNode);
        }
        return resultString;
    }

    @Override
    public MapFunctionNode newInstance() {
        return new MapFunctionNode();
    }

    @Override
    public void fillContentParameters(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        fillContentParametersRandom(childNode);
    }

    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        Integer functionCnt = fillContentParameterFunctions(childNode);
        inferResultRecorder(functionCnt);
    }

    private Integer fillContentParameterFunctions(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
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
        InformationTreeNode dummyChildNode = InformationTreeFunctionNodeManager.getInstance().getMapDummyChildNode(childNode);
        int cnt = 0;
        for(int i = 0; i < functionCnt; i ++) {
            InformationTreeFunctionNodeManager.getInstance().setMapLock();
            InformationTreeNode treeNode = informationTreeGenerator.buildInformationTree(dummyChildNode,
                    GlobalRandom.getInstance().nextInt(3), !GlobalSettings.starNodeSelection,
                    GlobalSettings.starNodeSelection, true);
            InformationTreeFunctionNodeManager.getInstance().unLockMapLock();
            childList.add(treeNode);
            if(treeNode instanceof AttributeFunctionNode) {
                cnt ++;
            }
        }
        if(cnt < functionCnt) mixAttrFlag = true;
        return functionCnt;
    }

    private void inferResultRecorder(Integer functionCnt) {
        XMLDatatypeComplexRecorder recorder = new XMLDatatypeComplexRecorder();
        if(functionCnt > 1 || childList.get(0).datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            recorder.xmlDatatype = XMLDatatype.SEQUENCE;
        }
        XMLDatatype actualType = null;
        for(int i = 1; i <= functionCnt; i ++) {
            if(((InformationTreeNode) childList.get(i)).datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
                recorder.xmlDatatype = XMLDatatype.SEQUENCE;
            if(actualType != XMLDatatype.MIXED) {
                if (actualType == null) {
                    actualType = ((InformationTreeNode) childList.get(i)).datatypeRecorder.getActualDatatype();
                } else if (actualType != ((InformationTreeNode) childList.get(i)).datatypeRecorder.getActualDatatype())
                    actualType = XMLDatatype.MIXED;
            }
        }
        if(recorder.xmlDatatype == XMLDatatype.SEQUENCE)
            recorder.subDatatype = actualType;
        else recorder.xmlDatatype = actualType;
        this.datatypeRecorder = recorder;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            return childNode.datatypeRecorder.subDatatype != XMLDatatype.MIXED &&
                    (childNode.datatypeRecorder.subDatatype != XMLDatatype.NODE || !childNode.datatypeRecorder.nodeMix);
        }
        return true;
    }
}
