package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public class EmptyFunctionNode extends InformationTreeSequenceFunctionNode {
    public EmptyFunctionNode() {
        functionExpr = "empty";
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
    }

    @Override
    public EmptyFunctionNode newInstance() {
        return new EmptyFunctionNode();
    }
}
