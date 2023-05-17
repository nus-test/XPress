package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class SortFunctionNode extends InformationTreeSequenceFunctionNode {

    public SortFunctionNode() {
        functionExpr = "sort";
    }

    @Override
    public SortFunctionNode newInstance() {
        return new SortFunctionNode();
    }
}
