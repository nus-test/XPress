package XTest.XPathGeneration.PredicateGeneration;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.AttributeNode;
import XTest.XMLGeneration.ContextNode;
import XTest.XMLGeneration.ElementNode;

public class PredicateTreeConstantNode extends PredicateTreeNode implements UnaryPredicateTreeNode {

    public PredicateTreeConstantNode(XMLDatatype xmlDatatype, String context) {
        this(xmlDatatype, context, context);
        if(datatype == XMLDatatype.STRING)
            this.XPathExpr = "\"" + context + "\"";
        if(datatype == XMLDatatype.DURATION)
            XPathExpr = "xs:duration('" + context + "')";
        if(datatype == XMLDatatype.BOOLEAN)
            XPathExpr = context + "()";
    }

    public PredicateTreeConstantNode(XMLDatatype xmlDatatype, String context, String XPathExpr) {
        datatype = xmlDatatype;
        dataContent = context;
        this.XPathExpr = XPathExpr;
    }

    public PredicateTreeConstantNode(XMLDatatype xmlDatatype, XMLDatatype subXMLDatatype, String context, String XPathExpr) {
        datatype = xmlDatatype;
        subDatatype = subXMLDatatype;
        dataContent = context;
        this.XPathExpr = XPathExpr;
    }

    public PredicateTreeConstantNode(ElementNode elementNode) {
        this(elementNode, null);
    }

    public PredicateTreeConstantNode(ElementNode elementNode, String XPathExpr) {
        dataContent = elementNode.dataContext;
        datatype = elementNode.dataType;
        if(XPathExpr == null) {
            if (elementNode instanceof AttributeNode)
                this.XPathExpr = "@" + elementNode.tagName;
        }
        else this.XPathExpr = XPathExpr;
    }

    public PredicateTreeConstantNode(String XPathExpr, XMLDatatype datatype) {
        this.datatype = datatype;
        this.XPathExpr = XPathExpr;
    }

    @Override
    public String toString() {
        return XPathExpr;
    }
}
