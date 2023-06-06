package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

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
