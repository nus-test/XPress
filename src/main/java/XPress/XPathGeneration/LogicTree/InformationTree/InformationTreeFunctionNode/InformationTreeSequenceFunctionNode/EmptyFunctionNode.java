package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLBoolean;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

//@FunctionV3
public class EmptyFunctionNode extends InformationTreeSequenceFunctionNode {
    public EmptyFunctionNode() {
        functionExpr = "empty";
        datatypeRecorder.xmlDatatype = XMLBoolean.getInstance();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
    }

    @Override
    public EmptyFunctionNode newInstance() {
        return new EmptyFunctionNode();
    }
}
