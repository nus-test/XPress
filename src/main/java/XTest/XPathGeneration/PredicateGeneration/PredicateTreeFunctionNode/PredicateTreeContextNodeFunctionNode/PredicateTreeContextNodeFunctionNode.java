package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeContextNodeFunctionNode;

import XTest.DefaultListHashMap;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.LowerCaseFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PredicateTreeContextNodeFunctionNode extends PredicateTreeFunctionNode {
    public static DefaultListHashMap<XMLDatatype, PredicateTreeContextNodeFunctionNode> outputDataTypeMap = new DefaultListHashMap<>();
    static {
        insertFunctionToMap(new HasChildrenFunctionNode());
        insertFunctionToMap(new LastFunctionNode());
        insertFunctionToMap(new PositionFunctionNode());
    }

    static void insertFunctionToMap(PredicateTreeContextNodeFunctionNode functionNode) {
        outputDataTypeMap.get(functionNode.datatype).add(functionNode);
    }

    public abstract PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode);

    public String getSubContentXPathGenerator(String XPathPrefix, ContextNode currentNode) {
        return XPathPrefix + "[@id=\"" + currentNode.id + "\"]/" + XPathExpr + "()";
    }

    public String getSubContentXPathGenerator(String XPathPrefix) {
        return XPathPrefix + "[@id=\"" + childList.get(0).dataContent + "\"]/" + XPathExpr + "()";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    public void fillContents(ContextNode inputNode) {
        PredicateTreeConstantNode treeNode = new PredicateTreeConstantNode(XMLDatatype.NODE, Integer.toString(inputNode.id));
        childList.add(treeNode);
    }
    @Override
    public abstract PredicateTreeContextNodeFunctionNode newInstance();
}

