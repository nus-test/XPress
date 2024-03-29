package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLNode;
import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.GlobalRandom;
import XPress.TestException.DebugErrorException;
import XPress.XMLGeneration.AttributeNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XPress.XPathGeneration.LogicTree.LogicTreeNode;
@FunctionV1
public class AttributeFunctionNode extends InformationTreeDirectContentFunctionNode {

    @Override
    public String getCalculationString(LogicTreeNode parentNode, boolean checkImpact) throws DebugErrorException {
        String calculationStr = childList.get(0).getCalculationString(parentNode, false)
                + "/@" + functionExpr;
        if(!(datatypeRecorder.xmlDatatype instanceof XMLSequence)) {
            calculationStr = "(" + calculationStr + " cast as " + datatypeRecorder.xmlDatatype.officialTypeName + ")";
        }
        return calculationStr;
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        int nodeId;
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLNode) {
            nodeId = Integer.parseInt(childNode.getContext().context);
        }
        else {
            // Else is applied to a sequence of nodes
            nodeId = Integer.parseInt(childNode.getContext().supplementaryContext);
        }
        fillContentParametersWithNodeID(childNode, nodeId);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        int nodeId = GlobalRandom.getInstance().nextInt(contextInfo.mainExecutor.maxId) + 1;
        fillContentParametersWithNodeID(childNode, nodeId);
    }

    private void fillContentParametersWithNodeID(InformationTreeNode childNode, Integer nodeId) {
        AttributeNode attributeNode = GlobalRandom.getInstance().getRandomFromList(
                contextInfo.mainExecutor.contextNodeMap.get(nodeId).attributeList);
        functionExpr = attributeNode.tagName;
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLNode) {
            datatypeRecorder.xmlDatatype = attributeNode.dataType;
        }
        else {
            datatypeRecorder.xmlDatatype = XMLSequence.getInstance();
            datatypeRecorder.subDatatype = attributeNode.dataType;
        }
    }

    @Override
    public AttributeFunctionNode newInstance() {
        return new AttributeFunctionNode();
    }

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        String expr = getXPathExpressionCheck(returnConstant, parentNode, calculateString);
        if(expr != null) return expr;
        String childExpr = childList.get(0).getXPathExpression();
        if(childExpr.equals(".")) {
            return "@" + functionExpr;
        }
        return childExpr + "/@" + functionExpr;
    }
}
