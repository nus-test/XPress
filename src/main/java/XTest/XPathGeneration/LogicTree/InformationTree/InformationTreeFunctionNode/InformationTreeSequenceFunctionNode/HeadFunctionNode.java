package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.PrimitiveDatatype.XMLAtomic;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

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
