package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import static XTest.StringUtils.getListString;

public class ConcatFunctionNode extends PredicateTreeFunctionNode {
    ConcatFunctionNode() {
        this.datatype = XMLDatatype.STRING;
        XPathExpr = "concat";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        String randomString = XMLDatatype.STRING.getValueHandler().getValue(false);
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode(XMLDatatype.STRING, randomString);
        childList.add(constantNode);
    }

    @Override
    public ConcatFunctionNode newInstance() {
        return new ConcatFunctionNode();
    }
}
