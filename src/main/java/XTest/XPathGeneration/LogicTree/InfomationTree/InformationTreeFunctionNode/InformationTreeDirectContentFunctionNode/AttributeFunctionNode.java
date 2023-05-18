package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.AttributeNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class AttributeFunctionNode extends InformationTreeDirectContentFunctionNode {

    @Override
    public String getCurrentLevelCalculationString() {
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            return getSequenceCalculationString();
        String calculationStr = getXPathExpression(false) + "[@id=\"" + childList.get(0).context + "\"]";
        calculationStr += "/@" + functionExpr;
        return calculationStr;
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        int nodeId;
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            nodeId = Integer.parseInt(childNode.context);
        }
        else {
            // Else is applied to a sequence of nodes
            nodeId = Integer.parseInt(childNode.supplementaryContext);
        }
        AttributeNode attributeNode = GlobalRandom.getInstance().getRandomFromList(
                mainExecutor.contextNodeMap.get(nodeId).attributeList);
        functionExpr = attributeNode.tagName;
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            datatypeRecorder.xmlDatatype = attributeNode.dataType;
        }
        else {
            datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            datatypeRecorder.subDatatype = attributeNode.dataType;
        }
    }

    @Override
    public AttributeFunctionNode newInstance() {
        return new AttributeFunctionNode();
    }

    public String getXPathExpression(boolean returnConstant) {
        String expr = getXPathExpressionCheck(returnConstant);
        if(expr != null) return expr;
        String childExpr = childList.get(0).getXPathExpression();
        if(childExpr.equals(".")) {
            return "@" + functionExpr;
        }
        return childExpr + "/@" + functionExpr;
    }
}
