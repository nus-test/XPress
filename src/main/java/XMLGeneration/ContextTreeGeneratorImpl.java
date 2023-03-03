package XMLGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContextTreeGeneratorImpl implements ContextTreeGenerator {

    @Override
    public ContextNode GenerateRandomTree(int contextTreeSize) {
        List<ContextNode> nodeList = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < contextTreeSize; i ++) {
            ContextNode currentNode = new ContextNode();
            nodeList.add(currentNode);
            if(i != 0) {
                int parent = random.nextInt(i);
                currentNode.parentNode = nodeList.get(parent);
                nodeList.get(parent).childList.add(currentNode);
            }
        }
        return nodeList.get(0);
    }
}
