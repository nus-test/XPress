package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class NormalizeSpaceFunctionNode extends InformationTreeFunctionNode {
    NormalizeSpaceFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "normalize-space";
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        fillContentsRandom(childNode);
    }
    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.STRING;
    }

    @Override
    public NormalizeSpaceFunctionNode newInstance() {
        return new NormalizeSpaceFunctionNode();
    }
}