package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class HeadFunctionNode extends InformationTreeSequenceFunctionNode {
    HeadFunctionNode() {
        functionExpr = "head";
    }

    @Override
    public HeadFunctionNode newInstance() {
        return new HeadFunctionNode();
    }
}
