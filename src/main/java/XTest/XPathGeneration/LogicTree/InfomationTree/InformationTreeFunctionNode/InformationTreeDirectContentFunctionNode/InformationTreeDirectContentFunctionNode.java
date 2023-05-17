package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public abstract class InformationTreeDirectContentFunctionNode extends InformationTreeFunctionNode {

    @Override
    public void fillContents(InformationTreeNode childNode) {
        fillContentsRandom(childNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        Boolean checkResult = recorder.xmlDatatype == XMLDatatype.NODE;
        if(!checkResult) {
            if(recorder.xmlDatatype == XMLDatatype.SEQUENCE && recorder.subDatatype == XMLDatatype.NODE)
                checkResult = true;
        }
        return checkResult;
    }

    /**
     * 
     * @return XPath expression string for calculating the result of current information tree. If results in single value should evaluate
     * to single value, if is a sequence should give XPath expression of sequence.
     */
    public String getCurrentLevelCalculationString() {
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            return getSequenceCalculationString();
        String calculationStr = getXPathExpression(false) + "[@id=\"" + childList.get(0).context + "\"]";
        calculationStr += "/" + functionExpr + "()";
        return calculationStr;
    }

    public String getSequenceCalculationString() {
        return getXPathExpression(true);
    }
}
