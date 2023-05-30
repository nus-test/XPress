package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;

@FunctionV3
public class HeadFunctionNode extends InformationTreeSequenceFunctionNode {
    public HeadFunctionNode() {
        functionExpr = "head";
    }

    @Override
    public HeadFunctionNode newInstance() {
        return new HeadFunctionNode();
    }
}
