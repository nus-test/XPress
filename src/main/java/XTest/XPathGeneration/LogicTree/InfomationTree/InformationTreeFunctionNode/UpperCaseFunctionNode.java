package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class UpperCaseFunctionNode extends InformationTreeFunctionNode {
    public UpperCaseFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "upper-case";
    }
    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.STRING;
    }
    @Override
    public UpperCaseFunctionNode newInstance() {
        return new UpperCaseFunctionNode();
    }
}
