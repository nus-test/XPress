package XTest.XMLGeneration;

import XTest.GlobalRandom;

import java.util.ArrayList;
import java.util.List;

public class ContextNode extends ElementNode {
    public List<AttributeNode> attributeList = new ArrayList<>();
    public List<ContextNode> childList = new ArrayList<>();
    public List<ContextNode> childWithLeafList = new ArrayList<>();
    public int id;
    public int depth = 0;
    public int childId;
    public boolean hasPreceding = true;
    public boolean hasFollowing = true;
    public boolean hasLeaf = false;
    public int size;

    void addAttribute(AttributeNode attributeNode) {
        attributeNode.parentNode = this;
        attributeList.add(attributeNode);
    }

    void addChild(ContextNode contextNode) {
        contextNode.parentNode = this;
        contextNode.depth = this.depth + 1;
        childList.add(contextNode);
        contextNode.childId = childList.size();
    }

    void assignTemplate(ContextNode contextNode) {
        assignTemplate(contextNode, false);
    }

    void assignTemplate(ContextNode contextNode, boolean inheritLeaf) {
        this.tagName = contextNode.tagName;
        this.dataType = contextNode.dataType;
        for(AttributeNode attributeNode: contextNode.attributeList) {
            this.attributeList.add(new AttributeNode(attributeNode));
        }
        if(inheritLeaf)
            hasLeaf = contextNode.hasLeaf;
    }

    public void assignChildIterative(ContextNode contextNode) {
        ContextNode duplicatedChildNode = getDuplicateIterative(contextNode);
        addChild(duplicatedChildNode);
        if(contextNode.hasLeaf) {
            childWithLeafList.add(duplicatedChildNode);
            hasLeaf = true;
        }
    }

    ContextNode getDuplicateIterative(ContextNode contextNode) {
        ContextNode node = new ContextNode();
        node.assignTemplate(contextNode, true);
        for(ContextNode contextNodeChild : contextNode.childList) {
            ContextNode child = getDuplicateIterative(contextNodeChild);
            node.addChild(child);
            if(child.hasLeaf)
                node.childWithLeafList.add(child);
        }
        return node;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getStrPathToSpecifiedLeafNode() {
        if(!hasLeaf) return null;
        if(childList.size() == 0) return "";
        if(childWithLeafList.size() == 0) {
            System.out.println("-----------------------");
            System.out.println(id);
            System.out.println(hasLeaf);
            System.out.println(childList.size());
            System.out.println(childWithLeafList.size());
            System.out.println("+++++++++++++++++++++++");
        }
        ContextNode childWithLeaf = GlobalRandom.getInstance().getRandomFromList(childWithLeafList);
        String childPath = childWithLeaf.getStrPathToSpecifiedLeafNode();
        String path = childWithLeaf.tagName;
        if(childPath != null && childPath.length() != 0)
            path += "/";
        path += childPath;
        return path;
    }

    public String getStrPathToRandomChildNode(boolean child) {
        if(childList.size() == 0) return "";
        ContextNode childNode = GlobalRandom.getInstance().getRandomFromList(childList);
        double prob = GlobalRandom.getInstance().nextDouble();
        String childPath;
        if(prob < 0.3 || !child) {
            childPath = childNode.getStrPathToRandomChildNode(true);
        }
        else return "";
        String path = childNode.tagName;
        if(childPath != null && childPath.length() != 0)
            path += "/";
        path += childPath;
        return path;
    }

    public String getStrPathToRandomChildNode() {
        return getStrPathToRandomChildNode(false);
    }
}
