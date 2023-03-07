package XTest.XMLGeneration;

public class XMLWriter {
    String XMLWriter(ContextNode root) {
        String XMLBuilder = new String();
        return writeContext(XMLBuilder, root);
    }

    String newLine(String currentBuilder, Boolean withStructure) {
        if(withStructure) currentBuilder += "\n";
        return currentBuilder;
    }

    String newTab(String currentBuilder, Boolean withStructure) {
        if(withStructure) currentBuilder += "\t";
        return currentBuilder;
    }

    String writeContext(String currentBuilder, ContextNode currentNode, Boolean withStructure) {
        String preSpace = "";
        if(withStructure)
            for(int i = 0; i < currentNode.depth; i ++)
                preSpace += "\t";
        currentBuilder += preSpace;
        currentBuilder += "<" + currentNode.tagName;
        for(AttributeNode attributeNode : currentNode.attributeList) {
            currentBuilder += " ";
            currentBuilder = writeAttribute(currentBuilder, attributeNode);
        }
        currentBuilder += ">";
        for (ContextNode childNode : currentNode.childList) {
            currentBuilder = newLine(currentBuilder, withStructure);
            currentBuilder = writeContext(currentBuilder, childNode, withStructure);
        }
        currentBuilder = newLine(currentBuilder, withStructure);
        currentBuilder = newTab(currentBuilder, withStructure);
        currentBuilder += preSpace + currentNode.dataContext;
        currentBuilder = newLine(currentBuilder, withStructure);
        currentBuilder += preSpace + "</" + currentNode.tagName + ">";
        return currentBuilder;
    }

    String writeContext(String currentBuilder, ContextNode currentNode) {
        return writeContext(currentBuilder, currentNode, false);
    }

    String writeAttribute(String currentBuilder, AttributeNode currentNode) {
        currentBuilder += currentNode.tagName + "=\"" + currentNode.dataContext + "\"";
        return currentBuilder;
    }
}
