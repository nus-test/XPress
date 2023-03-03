package XMLGeneration;

public class XMLWriter {
    String XMLWriter(ContextNode root) {
        String XMLBuilder = new String();
        return writeContext(XMLBuilder, root);
    }

    String writeContext(String currentBuilder, ContextNode currentNode) {
        currentBuilder = "<" + currentNode.tagName + currentBuilder;
        for(AttributeNode attributeNode : currentNode.attributeList) {
            currentBuilder += " ";
            currentBuilder = writeAttribute(currentBuilder, attributeNode);
        }
        currentBuilder += ">";
        for(ContextNode childNode : currentNode.childList)
            currentBuilder = writeContext(currentBuilder, childNode);
        currentBuilder += currentNode.dataContext + "<\\" + currentNode.tagName + ">";
        return currentBuilder;
    }

    String writeAttribute(String currentBuilder, AttributeNode currentNode) {
        currentBuilder += currentNode.tagName + "=" + currentNode.dataContext;
        return currentBuilder;
    }
}
