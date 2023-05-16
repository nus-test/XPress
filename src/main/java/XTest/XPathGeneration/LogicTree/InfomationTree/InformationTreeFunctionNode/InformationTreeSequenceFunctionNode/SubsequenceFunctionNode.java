package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.GlobalRandom;
import XTest.Pair;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class SubsequenceFunctionNode extends InformationTreeSequenceFunctionNode {
    SubsequenceFunctionNode() {
        functionExpr = "subsequence";
    }

    @Override
    public SubsequenceFunctionNode newInstance() {
        return new SubsequenceFunctionNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        int originalSequenceLength;
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            originalSequenceLength = Integer.parseInt(childNode.context);
        else originalSequenceLength = 1;
        fillContentsWithSequenceLength(childNode, originalSequenceLength);
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        fillContentsWithSequenceLength(childNode, GlobalRandom.getInstance().nextInt(100) + 1);
    }

    private void fillContentsWithSequenceLength(InformationTreeNode childNode, Integer originalSequenceLength) {
        Pair pair = GlobalRandom.getInstance().nextInterval(originalSequenceLength);
        int length = pair.y - pair.x + 1;
        double prob = GlobalRandom.getInstance().nextDouble();
        childList.add(childNode);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, Integer.toString(pair.x + 1)));
        if(prob < 0.5) {
            childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, Integer.toString(length)));
        }
    }
}
