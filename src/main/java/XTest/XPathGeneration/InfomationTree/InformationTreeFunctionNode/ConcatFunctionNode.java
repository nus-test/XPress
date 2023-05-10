package XTest.XPathGeneration.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class ConcatFunctionNode extends InformationTreeFunctionNode {
    ConcatFunctionNode() {
        this.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "concat";
    }

    @Override
    public ConcatFunctionNode newInstance() {
        return new ConcatFunctionNode();
    }

    @Override
    void fillContents(InformationTreeNode childNode) {
        fillContentsRandom(childNode);
    }

    @Override
    void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        String randomString = XMLDatatype.STRING.getValueHandler().getValue(false);
        InformationTreeConstantNode constantNode = new InformationTreeConstantNode(XMLDatatype.STRING, randomString);
        childList.add(constantNode);
    }
}
