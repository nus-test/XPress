package XTest.XPathGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.AttributeNode;
import XTest.XMLGeneration.ContextNode;
import XTest.XMLGeneration.XMLWriter;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeContextNodeFunctionNode.AttributeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeContextNodeFunctionNode.PredicateTreeContextNodeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeContextNodeFunctionNode.TextFunctionNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SequenceGenerator {
    MainExecutor mainExecutor;

    SequenceGenerator() {}

    SequenceGenerator(MainExecutor mainExecutor) {
        this.mainExecutor = mainExecutor;
    }

    public PredicateTreeConstantNode generateNodeSequenceFromContext(int length, List<ContextNode> currentNodeList) {;
        String stringBuild = "(";
        boolean start = true;
        Map<String, Boolean> checkMap = new HashMap<>();
        for(int i = 0; i < length; i ++) {
            String node = generateSingleNodeExprFromContext(currentNodeList);
            if(!node.contains("<")) {
                if(checkMap.containsKey(node)) {
                    continue;
                }
                else checkMap.put(node, true);
            }
            if(!start) stringBuild += ",";
            stringBuild += node;
            start = false;
        }
        stringBuild += ")";
        return new PredicateTreeConstantNode(XMLDatatype.SEQUENCE, XMLDatatype.NODE,
                stringBuild, stringBuild);
    }
    
    public PredicateTreeConstantNode generateSequenceFromContext(int length, XMLDatatype xmlDatatype, List<ContextNode> currentNodeList) {
        String stringBuild = "(";
        boolean start = true;
        for(int i = 0; i < length; i ++) {
            String element = generateSingleElementExprFromContext(xmlDatatype, currentNodeList);
            if(!start) stringBuild += ",";
            stringBuild += element;
            start = false;
        }
        stringBuild += ")";
        return new PredicateTreeConstantNode(XMLDatatype.SEQUENCE, xmlDatatype,
                stringBuild, stringBuild);
    }
    
    public PredicateTreeConstantNode generateConstantSequence(int length, XMLDatatype xmlDatatype) {
        String stringBuild = "(";
        boolean start = true;
        for(int i = 0; i < length; i ++) {
            String element = generateSingleElementExpr(xmlDatatype);
            if(!start) stringBuild += ",";
            stringBuild += element;
            start = false;
        }
        stringBuild += ")";
        return new PredicateTreeConstantNode(XMLDatatype.SEQUENCE, xmlDatatype,
                stringBuild, stringBuild);
    }
    
    public PredicateTreeConstantNode generateConstantNodeSequence(int length) {
        String stringBuild = "(";
        boolean start = true;
        for(int i = 0; i < length; i ++) {
            String element = generateSingleConstantNodeExpr();
            if(!start) stringBuild += ",";
            stringBuild += element;
            start = false;
        }
        stringBuild += ")";
        return new PredicateTreeConstantNode(XMLDatatype.SEQUENCE, XMLDatatype.NODE,
                stringBuild, stringBuild);
    }

    String generateSingleNodeExprFromContext(List<ContextNode> currentNodeList) {
        double prob = GlobalRandom.getInstance().nextDouble();
        ContextNode starredNode = GlobalRandom.getInstance().getRandomFromList(currentNodeList);
        if(prob < 0.7 && starredNode.childList.size() != 0)
            return starredNode.getStrPathToRandomChildNode();
        return starredNode.getPath();
        //return generateSingleConstantNodeExpr();
    }
    
    String generateSingleConstantNodeExpr() {
        return XMLWriter.writeContext(new String(),
                GlobalRandom.getInstance().getRandomFromList(mainExecutor.extraLeafNodeList));
    }
    
    String generateSingleElementExprFromContext(XMLDatatype xmlDatatype, List<ContextNode> currentNodeList) {
        int trial = 3;
        List<PredicateTreeContextNodeFunctionNode> candidateList = new ArrayList<>();
        for(int i = 0; i < trial; i ++) {
            ContextNode contextNode = GlobalRandom.getInstance().getRandomFromList(currentNodeList);
            if(contextNode.dataType == xmlDatatype)
                candidateList.add(new TextFunctionNode(contextNode));
            for(AttributeNode attributeNode : contextNode.attributeList)
                if(contextNode.dataType == xmlDatatype)
                    candidateList.add(new AttributeFunctionNode(attributeNode));
            if(!candidateList.isEmpty())
                break;
        }
        for(PredicateTreeContextNodeFunctionNode functionNode : PredicateTreeContextNodeFunctionNode.outputDataTypeMap.get(xmlDatatype)) {
            candidateList.add(functionNode.newInstance());
        }
        if(!candidateList.isEmpty()) {
            PredicateTreeContextNodeFunctionNode node =
                    GlobalRandom.getInstance().getRandomFromList(candidateList);
            return node.XPathExpr;
        }
        return generateSingleElementExpr(xmlDatatype);
    }

    String generateSingleElementExpr(XMLDatatype xmlDatatype) {
        if(xmlDatatype.getValueHandler() != null) {
            String value = xmlDatatype.getValueHandler().getValue();
            return XMLDatatype.wrapExpression(value, xmlDatatype);
        }
        return null;
    }
}
