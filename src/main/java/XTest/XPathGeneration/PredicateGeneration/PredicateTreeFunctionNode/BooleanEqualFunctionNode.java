package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLBooleanHandler;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import static XTest.StringUtils.getListString;

public class BooleanEqualFunctionNode extends PredicateTreeFunctionNode {
    BooleanEqualFunctionNode() {
        this.datatype = XMLDatatype.BOOLEAN;
        this.XPathExpr = "boolean-equal";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode
                (XMLDatatype.BOOLEAN, XMLDatatype.BOOLEAN.getValueHandler().getValue());
        childList.add(constantNode);
    }

    @Override
    public BooleanEqualFunctionNode newInstance() {
        return new BooleanEqualFunctionNode();
    }
}
