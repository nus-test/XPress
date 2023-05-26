package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public class ExistsFunctionNode extends InformationTreeSequenceFunctionNode {
    public ExistsFunctionNode() {
        functionExpr = "exists";
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
    }

    @Override
    public ExistsFunctionNode newInstance() {
        return new ExistsFunctionNode();
    }
}
