package XTest.XPathGeneration.PredicateGeneration;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;

import java.util.ArrayList;
import java.util.List;

public abstract class PredicateTreeNode {
    public XMLDatatype datatype;
    public XMLDatatype subDatatype;
    public int sequenceLength;
    public String dataContent;
    public String XPathExpr;
    public ContextNode contextNode;
    public List<PredicateTreeNode> childList = new ArrayList<>();

    public abstract String toString();
}
