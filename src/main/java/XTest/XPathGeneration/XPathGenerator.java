package XTest.XPathGeneration;

import XTest.XMLGeneration.ContextNode;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XPathGenerator {
    Map<Integer, ContextNode> contextNodeMap = new HashMap<>();

    XPathGenerator(ContextNode root) {
        getContextNodeMap(root);
    }

    void getContextNodeMap(ContextNode currentNode) {
        contextNodeMap.put(currentNode.id, currentNode);
        for(ContextNode childNode : currentNode.childList)
            getContextNodeMap(childNode);
    }

    String getXPath(String currentBuilder, List<ContextNode> currentNodeList, int depth) {
        if(depth == 0) {
            return currentBuilder;
        }
        return null;
    }
}
