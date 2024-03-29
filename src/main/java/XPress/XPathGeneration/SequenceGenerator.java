package XPress.XPathGeneration;

import XPress.DatabaseExecutor.MainExecutor;
import XPress.GlobalRandom;
import XPress.GlobalSettings;
import XPress.XMLGeneration.ContextNode;
import XPress.XMLGeneration.XMLWriter;

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
        ContextNode starredNode = (currentNodeList.size() > 0 && (GlobalSettings.starNodeSelection || GlobalSettings.targetedSectionPrefix)) ?
            GlobalRandom.getInstance().getRandomFromList(currentNodeList) :
            mainExecutor.contextNodeMap.get(GlobalRandom.getInstance().nextInt(mainExecutor.maxId) + 1);
        if(prob < 0.8 && starredNode.childList.size() != 0)
            return starredNode.getStrPathToRandomChildNode();
        return starredNode.getPath();
        //return generateSingleConstantNodeExpr();
    }
    
    String generateSingleConstantNodeExpr() {
        return XMLWriter.writeContext(new String(),
                GlobalRandom.getInstance().getRandomFromList(mainExecutor.extraLeafNodeList));
    }
}
