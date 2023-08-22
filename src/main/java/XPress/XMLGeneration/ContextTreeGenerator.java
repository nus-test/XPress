package XPress.XMLGeneration;

import java.util.List;

public interface ContextTreeGenerator {
    ContextNode generateRandomTree(int contextTreeSize, List<String> namespaceList);

    public static void markPrecedingFollowing(ContextNode currentNode, MarkChoice currentMarking) {
        if(currentMarking == MarkChoice.PRECEDING)
            currentNode.hasPreceding = false;
        else
            currentNode.hasFollowing = false;
        if(currentNode.childList.size() != 0) {
            int id = (currentMarking == MarkChoice.PRECEDING) ? 0 : (currentNode.childList.size() - 1);
            markPrecedingFollowing(currentNode.childList.get(id), currentMarking);
        }
    }

    public enum MarkChoice {PRECEDING, FOLLOWING}
}
