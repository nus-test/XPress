package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

public class InformationTreeMapNode extends InformationTreeFunctionNode {

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        // TODO: Check the first child type and decide whether needs to be wrapped by "()"

        String builder = childList.get(0).getXPathExpression(returnConstant) + "! ";
        if(childList.size() > 2)
            builder += "(";
        boolean start = true;
        for(int i = 1; i < childList.size(); i ++) {
            if(!start) builder += ",";
            builder += ((InformationTreeFunctionNode) childList.get(i)).getCurrentContextFunctionExpr();
            start = false;
        }
        if(childList.size() > 2)
            builder += ")";
        return builder;
    }

    @Override
    public InformationTreeFunctionNode newInstance() {
        return new InformationTreeMapNode();
    }

    @Override
    public void fillContentParameters(InformationTreeNode childNode) {

    }

    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) {

    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return null;
    }
}
