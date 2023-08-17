package XPress.XMLGeneration;

public interface ContextTreeGenerator {
    ContextNode GenerateRandomTree(int contextTreeSize);

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
