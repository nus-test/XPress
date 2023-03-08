package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.DefaultHashMap;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import java.util.ArrayList;
import java.util.List;

public abstract class PredicateTreeFunctionNode extends PredicateTreeNode {
    static DefaultHashMap<XMLDatatype, List<PredicateTreeFunctionNode>> functionMap = new DefaultHashMap<>(new ArrayList<>());

    public static PredicateTreeFunctionNode getRandomPredicateTreeFunctionNode(XMLDatatype datatype) {
        List<PredicateTreeFunctionNode> functionList = functionMap.get(datatype);
        double prob = GlobalRandom.getInstance().nextDouble();
        if(functionList != null && functionList.size() > 0 && prob < 0.7)
            return GlobalRandom.getInstance().getRandomFromList(functionList).newInstance();
        return new ExistFunctionNode();
    }

    static void insertFunctionToMap(PredicateTreeFunctionNode functionNode, XMLDatatype datatype) {
        PredicateTreeFunctionNode.functionMap.get(datatype).add(functionNode);
    }

    public abstract void fillContents(PredicateTreeNode inputNode);

    public abstract PredicateTreeFunctionNode newInstance();
}
