package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class NoActionFunctionNode extends PredicateTreeFunctionNode {

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        this.datatype = inputNode.datatype;
        this.dataContent = inputNode.dataContent;
    }

    @Override
    public PredicateTreeFunctionNode newInstance() {
        return new NoActionFunctionNode();
    }

    @Override
    public String toString() {
        return childList.get(0).toString();
    }

    @Override
    public String calculationString() {
        String calculationStr = childList.get(0).dataContent;
        if(childList.get(0).datatype == XMLDatatype.BOOLEAN)
            calculationStr += "()";
        else if(childList.get(0).datatype == XMLDatatype.STRING)
            calculationStr = "\"" + calculationStr + "\"";
        return calculationStr;
    }
}
