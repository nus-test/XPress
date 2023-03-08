package XTest.XPathGeneration.PredicateGeneration;

import XTest.PrimitiveDatatype.XMLDatatype;

import java.util.ArrayList;
import java.util.List;

public abstract class PredicateTreeNode {
    public XMLDatatype datatype;
    public String dataContent;
    public String XPathExpr;
    public List<PredicateTreeNode> childList = new ArrayList<>();

    public abstract String toString();
}
