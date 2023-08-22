package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.DatatypeControl.PrimitiveDatatype.XMLNode;
import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.TestException.DebugErrorException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XPress.XPathGeneration.LogicTree.LogicTreeNode;

@FunctionV3
public class PositionFunctionNode extends InformationTreeDirectContentFunctionNode {
    public PositionFunctionNode() {
        functionExpr = "position";
    }

    @Override
    public String getCalculationString(LogicTreeNode parentNode, boolean checkImpact) throws DebugErrorException {
        if(datatypeRecorder.xmlDatatype instanceof XMLSequence) {
            return super.getCalculationString(parentNode, checkImpact);
        }
        return "index-of((" + childList.get(0).getContextCalculationString() + ")/@id, \"" + childList.get(0).getContext().context + "\")";
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLNode) {
            datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        }
        else {
            datatypeRecorder.xmlDatatype = XMLSequence.getInstance();
            datatypeRecorder.subDatatype = XMLInteger.getInstance();
        }
    }

    @Override
    public PositionFunctionNode newInstance() {
        return new PositionFunctionNode();
    }
}
