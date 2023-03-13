package XTest.XMLGeneration;

import XTest.GlobalRandom;

import java.util.ArrayList;
import java.util.List;

public class ContextTreeGeneratorImpl implements ContextTreeGenerator {

    @Override
    public ContextNode GenerateRandomTree(int contextTreeSize) {
        List<ContextNode> nodeList = new ArrayList<>();
        for(int i = 0; i < contextTreeSize; i ++) {
            ContextNode currentNode = new ContextNode();
            nodeList.add(currentNode);
            currentNode.id = i + 1;
            if(i != 0) {
                int parent = GlobalRandom.getInstance().nextInt(i);
                nodeList.get(parent).addChild(currentNode);
            }
        }
        ContextNode root = nodeList.get(0);
        markPrecedingFollowing(root, MarkChoice.PRECEDING);
        markPrecedingFollowing(root, MarkChoice.FOLLOWING);
        return root;
    }

    public void markPrecedingFollowing(ContextNode currentNode, MarkChoice currentMarking) {
        if(currentMarking == MarkChoice.PRECEDING)
            currentNode.havePreceding = false;
        else
            currentNode.haveFollowing = false;
        if(currentNode.childList.size() != 0) {
            int id = (currentMarking == MarkChoice.PRECEDING) ? 0 : (currentNode.childList.size() - 1);
            markPrecedingFollowing(currentNode.childList.get(id), currentMarking);
        }
    }

    public enum MarkChoice {PRECEDING, FOLLOWING}
}
