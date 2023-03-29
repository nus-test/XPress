package XTest.XMLGeneration;

import java.util.ArrayList;
import java.util.List;

public class ContextNode extends ElementNode {
    public List<AttributeNode> attributeList = new ArrayList<>();
    public List<ContextNode> childList = new ArrayList<>();
    public List<ContextNode> childWithLeafList = new ArrayList<>();
    public int id;
    public int depth = 0;
    public int childId;
    public boolean havePreceding = true;
    public boolean haveFollowing = true;
    boolean hasLeaf = false;

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
        this.tagName = contextNode.tagName;
        this.dataType = contextNode.dataType;
        for(AttributeNode attributeNode: contextNode.attributeList) {
            this.attributeList.add(new AttributeNode(attributeNode));
        }
    }

    void setId(int id) {
        this.id = id;
    }
}
