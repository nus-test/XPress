package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.TestException.DebugErrorException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XPress.XPathGeneration.LogicTree.LogicTreeNode;

@FunctionV3
public class LastFunctionNode extends InformationTreeDirectContentFunctionNode {
    public LastFunctionNode() {
        functionExpr = "last";
    }

    @Override
    public String getCalculationString(LogicTreeNode parentNode, boolean checkImpact) throws DebugErrorException {
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            return super.getCalculationString(parentNode, checkImpact);
        }
        return "count(" + childList.get(0).getCalculationString(parentNode, false) + ")";
    }

    @Override
    public LastFunctionNode newInstance() {
        return new LastFunctionNode();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        }
        else {
            datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            datatypeRecorder.subDatatype = XMLDatatype.INTEGER;
        }
    }
}
