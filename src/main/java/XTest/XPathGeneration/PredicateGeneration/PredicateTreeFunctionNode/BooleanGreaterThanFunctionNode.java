package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class BooleanGreaterThanFunctionNode extends PredicateTreeFunctionNode {
    BooleanGreaterThanFunctionNode() {
        this.datatype = XMLDatatype.BOOLEAN;
        XPathExpr = "boolean-greater-than";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode
                (XMLDatatype.BOOLEAN, XMLDatatype.BOOLEAN.getValueHandler().getValue());
        childList.add(constantNode);
    }

    @Override
    public BooleanGreaterThanFunctionNode newInstance() {
        return new BooleanGreaterThanFunctionNode();
    }
}
