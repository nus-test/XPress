package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.Pair;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class SubstringFunctionNode extends InformationTreeFunctionNode {

    public SubstringFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "substring";
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(childNode.context == null) {
            fillContentsRandom(childNode);
            return;
        }
        fillContentsWithGivenLength(childNode, childNode.context.length());
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        fillContentsWithGivenLength(childNode,20);
    }

    private void fillContentsWithGivenLength(InformationTreeNode childNode, int length) {
        childList.add(childNode);
        double prob = GlobalRandom.getInstance().nextDouble();
        Pair interval = GlobalRandom.getInstance().nextInterval(length);
        InformationTreeConstantNode constantNodeStart = new InformationTreeConstantNode
                (XMLDatatype.INTEGER, Integer.toString(interval.x));
        InformationTreeConstantNode constantNodeLength = new InformationTreeConstantNode
                (XMLDatatype.INTEGER, Integer.toString(interval.y - interval.x + 1));
        childList.add(constantNodeStart);
        if(prob < 0.5)
            childList.add(constantNodeLength);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.STRING;
    }


    @Override
    public SubstringFunctionNode newInstance() {
        return new SubstringFunctionNode();
    }
}
