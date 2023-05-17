package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;

public class ExistsFunctionNode extends InformationTreeSequenceFunctionNode {
    public ExistsFunctionNode() {
        functionExpr = "exists";
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
    }

    @Override
    public ExistsFunctionNode newInstance() {
        return new ExistsFunctionNode();
    }
}
