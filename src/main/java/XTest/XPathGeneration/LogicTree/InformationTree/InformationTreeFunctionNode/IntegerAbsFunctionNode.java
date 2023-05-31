package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class IntegerAbsFunctionNode extends InformationTreeFunctionNode {
    public IntegerAbsFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "abs";
    }

    @Override
    public IntegerAbsFunctionNode newInstance() {
        return new IntegerAbsFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.INTEGER;
    }
}