package XTest.XPathGeneration.LogicTree.InfomationTree;

import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;

public class InformationTreeMapNode extends InformationTreeNode {

    @Override
    public String getXPathExpression(boolean returnConstant) {
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
}
