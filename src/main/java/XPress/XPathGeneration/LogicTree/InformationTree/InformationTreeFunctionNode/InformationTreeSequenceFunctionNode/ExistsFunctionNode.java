package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

//@FunctionV3
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
