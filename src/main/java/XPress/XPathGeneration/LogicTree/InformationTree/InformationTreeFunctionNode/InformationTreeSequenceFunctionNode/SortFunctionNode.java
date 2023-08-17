package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XPress.PrimitiveDatatype.XMLSimple;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.MapFunctionNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class SortFunctionNode extends InformationTreeSequenceFunctionNode {

    public SortFunctionNode() {
        functionExpr = "sort";
    }

    @Override
    public SortFunctionNode newInstance() {
        return new SortFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        if(childNode instanceof MapFunctionNode)
            if(((MapFunctionNode) childNode).mixAttrFlag)
                return false;
        if(!(childNode.datatypeRecorder.getActualDatatype().getValueHandler() instanceof XMLSimple))
            return false;
        try {
            childNode.contextInfo.mainExecutor.setReportLock();
            childNode.contextInfo.mainExecutor.executeSingleProcessor("sort(" + childNode.getCalculationString() + ")");
        } catch (Exception e) {
            if(childNode.contextInfo.mainExecutor != null)
                childNode.contextInfo.mainExecutor.unlockReportLock();
            return false;
        }
        return true;
    }
}
