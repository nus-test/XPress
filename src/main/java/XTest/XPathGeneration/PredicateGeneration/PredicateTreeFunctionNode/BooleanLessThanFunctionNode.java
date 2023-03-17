package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import static XTest.StringUtils.getListString;

public class BooleanLessThanFunctionNode extends PredicateTreeFunctionNode {
    BooleanLessThanFunctionNode() {
        this.datatype = XMLDatatype.BOOLEAN;
        XPathExpr = "boolean-less-than";
    }


    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode
                (XMLDatatype.BOOLEAN, XMLDatatype.BOOLEAN.getValueHandler().getValue());
        childList.add(constantNode);
    }

    @Override
    public BooleanLessThanFunctionNode newInstance() {
        return new BooleanLessThanFunctionNode();
    }
}
