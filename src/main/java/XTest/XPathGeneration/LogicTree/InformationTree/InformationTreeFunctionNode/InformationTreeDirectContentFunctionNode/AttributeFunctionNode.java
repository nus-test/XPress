package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.TestException.DebugErrorException;
import XTest.XMLGeneration.AttributeNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeContextNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import org.basex.core.cmd.Run;

public class AttributeFunctionNode extends InformationTreeDirectContentFunctionNode {

    @Override
    public String getCalculationString(LogicTreeNode parentNode, boolean checkImpact) throws DebugErrorException {
        String calculationStr = childList.get(0).getCalculationString(parentNode, false)
                + "/@" + functionExpr;
        if(datatypeRecorder.xmlDatatype != XMLDatatype.SEQUENCE) {
            calculationStr = "(" + calculationStr + " cast as " + datatypeRecorder.xmlDatatype.getValueHandler().officialTypeName + ")";
        }
        return calculationStr;
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        int nodeId;
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
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
        int nodeId = GlobalRandom.getInstance().nextInt(contextInfo.mainExecutor.maxId);
        fillContentParametersWithNodeID(childNode, nodeId);
    }

    private void fillContentParametersWithNodeID(InformationTreeNode childNode, Integer nodeId) {
        AttributeNode attributeNode = GlobalRandom.getInstance().getRandomFromList(
                contextInfo.mainExecutor.contextNodeMap.get(nodeId).attributeList);
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
