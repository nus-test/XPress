package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XPress.PrimitiveDatatype.XMLAtomic;
import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XPress.TestException.DebugErrorException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XPress.XPathGeneration.LogicTree.LogicTreeNode;

@FunctionV3
public class HeadFunctionNode extends InformationTreeSequenceFunctionNode {
    public HeadFunctionNode() {
        functionExpr = "head";
    }

    @Override
    public String getCalculationString(LogicTreeNode parentNode, boolean checkImpact) throws DebugErrorException {
        String calculationStr;
        if(datatypeRecorder.xmlDatatype != XMLDatatype.SEQUENCE &&
            datatypeRecorder.xmlDatatype.getValueHandler() instanceof XMLAtomic)
            calculationStr = "(" + functionExpr + "(" + childList.get(0).getCalculationString(parentNode, false)
                + ") cast as " + this.datatypeRecorder.getActualDatatype().getValueHandler().officialTypeName + ")";
        else {
            calculationStr = functionExpr + "(" + childList.get(0).getCalculationString(parentNode, false) + ")";
        }
        return calculationStr;
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        fillContentParametersRandom(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.getActualDatatype() == XMLDatatype.MIXED)
            datatypeRecorder = new XMLDatatypeComplexRecorder(childNode.datatypeRecorder);
        else datatypeRecorder.xmlDatatype = childNode.datatypeRecorder.getActualDatatype();
    }

    @Override
    public HeadFunctionNode newInstance() {
        return new HeadFunctionNode();
    }
}
