package XTest.XPathGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.AttributeNode;
import XTest.XMLGeneration.ContextNode;
import XTest.XMLGeneration.XMLWriter;
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

    public String generateNodeSequenceFromContext(int length, List<ContextNode> currentNodeList) {;
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
        return stringBuild;
    }
    
    public String generateConstantNodeSequence(int length) {
        String stringBuild = "(";
        boolean start = true;
        for(int i = 0; i < length; i ++) {
            String element = generateSingleConstantNodeExpr();
            if(!start) stringBuild += ",";
            stringBuild += element;
            start = false;
        }
        stringBuild += ")";
        return stringBuild;
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
}
