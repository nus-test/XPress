package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import org.apache.xpath.operations.Bool;

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
     * @return XPath expression string for calculating the result of current information tree which should result in single value.
     * If return type is sequence, an exception should be thrown.
     */
    public String getCurrentLevelCalculationString() {
        String calculationStr = getXPathExpression(false) + "[@id=\"" + childList.get(0).context + "\"]";
        calculationStr += "/" + functionExpr + "()";
        return calculationStr;
    }
}
