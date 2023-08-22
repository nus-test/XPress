package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.DatatypeControl.PrimitiveDatatype.XMLMixed;
import XPress.DatatypeControl.PrimitiveDatatype.XMLNode;
import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.GlobalRandom;
import XPress.GlobalSettings;
import XPress.DatatypeControl.XMLAtomic;
import XPress.DatatypeControl.XMLDatatypeComplexRecorder;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode.AttributeFunctionNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeGenerator;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XPress.XPathGeneration.LogicTree.LogicTreeNode;

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
        if(calculateString && datatypeRecorder.xmlDatatype instanceof XMLAtomic)
            return "((" + resultString + ") cast as " + datatypeRecorder.xmlDatatype.officialTypeName + ")";
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
        if(functionCnt > 1 || childList.get(0).datatypeRecorder.xmlDatatype instanceof XMLSequence) {
            recorder.xmlDatatype = XMLSequence.getInstance();
        }
        XMLDatatype actualType = null;
        for(int i = 1; i <= functionCnt; i ++) {
            if(((InformationTreeNode) childList.get(i)).datatypeRecorder.xmlDatatype instanceof XMLSequence)
                recorder.xmlDatatype = XMLSequence.getInstance();
            if(!(actualType instanceof XMLMixed)) {
                if (actualType == null) {
                    actualType = ((InformationTreeNode) childList.get(i)).datatypeRecorder.getActualDatatype();
                } else if (actualType != ((InformationTreeNode) childList.get(i)).datatypeRecorder.getActualDatatype())
                    actualType = XMLMixed.getInstance();
            }
        }
        if(recorder.xmlDatatype instanceof XMLSequence)
            recorder.subDatatype = actualType;
        else recorder.xmlDatatype = actualType;
        this.datatypeRecorder = recorder;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        if(GlobalSettings.starNodeSelection && childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence &&
            Integer.parseInt(childNode.context.context) == 0)
                return false;
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence) {
            return !(childNode.datatypeRecorder.subDatatype instanceof XMLMixed) &&
                    (!(childNode.datatypeRecorder.subDatatype instanceof XMLNode) || !childNode.datatypeRecorder.nodeMix);
        }
        return true;
    }
}
