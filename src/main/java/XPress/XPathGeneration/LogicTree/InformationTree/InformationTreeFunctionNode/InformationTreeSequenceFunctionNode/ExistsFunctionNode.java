package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLBoolean;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

//@FunctionV3
public class ExistsFunctionNode extends InformationTreeSequenceFunctionNode {
    public ExistsFunctionNode() {
        functionExpr = "exists";
        datatypeRecorder.xmlDatatype = XMLBoolean.getInstance();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
    }

    @Override
    public ExistsFunctionNode newInstance() {
        return new ExistsFunctionNode();
    }
}
