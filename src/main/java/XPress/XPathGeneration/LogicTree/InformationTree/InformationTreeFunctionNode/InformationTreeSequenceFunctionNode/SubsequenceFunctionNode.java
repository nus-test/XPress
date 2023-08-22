package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.DatatypeControl.PrimitiveDatatype.XMLNode;
import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.GlobalRandom;
import XPress.Pair;
import XPress.DatatypeControl.XMLDatatypeComplexRecorder;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
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
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence)
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
        childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), Integer.toString(pair.x + 1)));
        if(prob < 0.5) {
            childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), Integer.toString(length)));
        }
        boolean nodeMix = childNode.datatypeRecorder.getActualDatatype() instanceof XMLNode
                && childNode.datatypeRecorder.nodeMix;
        datatypeRecorder = new XMLDatatypeComplexRecorder(XMLSequence.getInstance(), childNode.datatypeRecorder.getActualDatatype(), nodeMix);
    }
}
