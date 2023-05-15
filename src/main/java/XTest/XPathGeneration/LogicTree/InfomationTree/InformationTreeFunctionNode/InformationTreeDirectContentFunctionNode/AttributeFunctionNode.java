package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.AttributeNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class AttributeFunctionNode extends InformationTreeDirectContentFunctionNode {

    @Override
    public String getCurrentLevelCalculationString() {
        String calculationStr = getXPathExpression(false) + "[@id=\"" + childList.get(0).context + "\"]";
        calculationStr += "/@" + functionExpr;
        return calculationStr;
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        int nodeId;
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            nodeId = Integer.parseInt(childNode.context);
        }
        else {
            // Else is applied to a sequence of nodes
            nodeId = Integer.parseInt(childNode.supplementaryContext);
        }
        AttributeNode attributeNode = GlobalRandom.getInstance().getRandomFromList(
                mainExecutor.contextNodeMap.get(nodeId).attributeList);
        functionExpr = attributeNode.tagName;
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            dataTypeRecorder.xmlDatatype = attributeNode.dataType;
        }
        else {
            dataTypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            dataTypeRecorder.subDatatype = attributeNode.dataType;
        }
    }

    @Override
    public AttributeFunctionNode newInstance() {
        return new AttributeFunctionNode();
    }
}
