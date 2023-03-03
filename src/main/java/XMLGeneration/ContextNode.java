package XMLGeneration;

import java.util.ArrayList;
import java.util.List;

public class ContextNode extends ElementNode {
    List<AttributeNode> attributeList = new ArrayList<>();
    List<ContextNode> childList = new ArrayList<>();

    void addAttribute(AttributeNode attributeNode) {
        attributeNode.parentNode = this;
        attributeList.add(attributeNode);
    }

    void addChild(ContextNode contextNode) {
        contextNode.parentNode = this;
        childList.add(contextNode);
    }

    void assignTemplate(ContextNode contextNode) {
        this.tagName = contextNode.tagName;
        this.dataType = contextNode.dataType;
        for(AttributeNode attributeNode: contextNode.attributeList) {
            this.attributeList.add(new AttributeNode(attributeNode));
        }
    }
}
