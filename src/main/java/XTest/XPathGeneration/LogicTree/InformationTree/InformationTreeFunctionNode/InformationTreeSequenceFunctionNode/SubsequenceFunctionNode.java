package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.GlobalRandom;
import XTest.Pair;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public class SubsequenceFunctionNode extends InformationTreeSequenceFunctionNode {
    public SubsequenceFunctionNode() {
        functionExpr = "subsequence";
    }

    @Override
    public SubsequenceFunctionNode newInstance() {
        return new SubsequenceFunctionNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        int originalSequenceLength;
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            originalSequenceLength = Integer.parseInt(childNode.getContext().context);
        else originalSequenceLength = 1;
        fillContentParametersWithSequenceLength(childNode, originalSequenceLength);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        fillContentParametersWithSequenceLength(childNode, GlobalRandom.getInstance().nextInt(100) + 1);
    }

    private void fillContentParametersWithSequenceLength(InformationTreeNode childNode, Integer originalSequenceLength) {
        if(originalSequenceLength == 0)
            originalSequenceLength = GlobalRandom.getInstance().nextInt(100) + 1;
        Pair pair = GlobalRandom.getInstance().nextInterval(originalSequenceLength);
        int length = pair.y - pair.x + 1;
        double prob = GlobalRandom.getInstance().nextDouble();
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, Integer.toString(pair.x + 1)));
        if(prob < 0.5) {
            childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, Integer.toString(length)));
        }
        boolean nodeMix = childNode.datatypeRecorder.getActualDatatype() == XMLDatatype.NODE
                && childNode.datatypeRecorder.nodeMix;
        datatypeRecorder = new XMLDatatypeComplexRecorder(XMLDatatype.SEQUENCE, childNode.datatypeRecorder.getActualDatatype(), nodeMix);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return true;
    }
}
