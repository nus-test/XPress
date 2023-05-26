package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

public class PositionFunctionNode extends InformationTreeDirectContentFunctionNode {
    public PositionFunctionNode() {
        functionExpr = "position";
    }

    @Override
    public String getCalculationString(LogicTreeNode parentNode, boolean checkImpact) throws DebugErrorException {
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            return super.getCalculationString(parentNode, checkImpact);
        }
        return "index-of((" + childList.get(0).getContextCalculationString() + ")/@id, \"" + childList.get(0).getContext().context + "\")";
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        }
        else {
            datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            datatypeRecorder.subDatatype = XMLDatatype.INTEGER;
        }
    }

    @Override
    public PositionFunctionNode newInstance() {
        return new PositionFunctionNode();
    }
}
