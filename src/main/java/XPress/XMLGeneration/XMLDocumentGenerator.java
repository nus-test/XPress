package XPress.XMLGeneration;

import XPress.GlobalRandom;
import XPress.PrimitiveDatatype.XMLDatatype;

import java.io.*;
import java.util.List;

import static java.lang.Math.max;

public class XMLDocumentGenerator {
    int maxId = 0;
    ContextTreeGenerator contextTreeGenerator = new ContextTreeGeneratorImpl();

    NameGenerator contextNodeNameGenerator = new BaseCharIDStyleNameGeneratorImpl('A');
    NameGenerator attributeNodeNameGenerator = new BaseCharIDStyleNameGeneratorImpl('a');
    AttributeTemplateGenerator attributeTemplateGenerator = new AttributeTemplateGeneratorImpl(attributeNodeNameGenerator);
    ContextTemplateGenerator contextTemplateGenerator =
            new ContextTemplateGeneratorImpl(contextNodeNameGenerator,
                    attributeTemplateGenerator);
    List<ContextNode> contextNodeTemplateList;
    List<ContextNode> leafContextNodeTemplateList;


    public XMLDocumentGenerator() {}

    public XMLDocumentGenerator(ContextTreeGenerator contextTreeGenerator) {
        this.contextTreeGenerator = contextTreeGenerator;
    }

    String getXMLDocument(int contextNodeSize) {
        ContextNode root = generateXMLDocument(contextNodeSize);
        return getXMLDocument(root);
    }

    String getXMLDocument(ContextNode root) {
        return XMLWriter.writeContext(new String(), root);
    }

    String getXMLDocumentWithStructure(int contextNodeSize) {
        ContextNode root = generateXMLDocument(contextNodeSize);
        return getXMLDocumentWithStructure(root);
    }

    String getXMLDocumentWithStructure(ContextNode root) {
        XMLWriter xmlWriter = new XMLWriter();
        return xmlWriter.writeContext(new String(), root, true);
    }

    ContextNode generateXMLDocumentSave2Resource(int contextNodeSize, String fileName) throws IOException {
        XMLContext xmlContext = generateXMLContext(contextNodeSize);
        FileWriter writer =
                new FileWriter((this.getClass().getResource(fileName).getPath()));
        writer.write(xmlContext.xmlContent);
        writer.flush();
        writer.close();
        return xmlContext.root;
    }

    public XMLContext generateXMLContext(int contextNodeSize) {
        ContextNode root = generateXMLDocument(contextNodeSize);
        String xmlData = getXMLDocument(root);
        return new XMLContext(root, xmlData);
    }

    public ContextNode generateXMLDocument(int contextNodeSize) {
        ContextNode root = contextTreeGenerator.GenerateRandomTree(contextNodeSize);
        int templateSize = max(1, contextNodeSize / 2);
        contextNodeTemplateList = contextTemplateGenerator.generateContextTemplate(templateSize);
        int leafTemplateSize = max(1, contextNodeSize / 3);
        leafContextNodeTemplateList = contextTemplateGenerator.generateContextTemplate(leafTemplateSize);
        assignTemplateToNode(root);
        maxId = contextNodeSize;
        return root;
    }

    public List<ContextNode> generateExtraLeafNodes(int contextNodeSize) {
        List<ContextNode> contextNodeList = contextTemplateGenerator.generateContextTemplate(contextNodeSize);
        for(ContextNode contextNode : contextNodeList) {
            maxId += 1;
            contextNode.id = maxId;
            assignValueToNode(contextNode);
        }
        return contextNodeList;
    }

    void assignTemplateToNode(ContextNode currentNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5 && currentNode.childList.size() == 0)
            currentNode.hasLeaf = true;
        ContextNode templateNode = GlobalRandom.getInstance().getRandomFromList(currentNode.hasLeaf
                 ? leafContextNodeTemplateList : contextNodeTemplateList);
        currentNode.assignTemplate(templateNode);
        assignValueToNode(currentNode);
        for(ContextNode childNode: currentNode.childList) {
            assignTemplateToNode(childNode);
            if(childNode.hasLeaf) {
                currentNode.hasLeaf = true;
                currentNode.childWithLeafList.add(childNode);
            }
        }
    }

    static void assignValueToNode(ContextNode currentNode) {
        currentNode.assignRandomValue();
        for(AttributeNode attributeNode: currentNode.attributeList) {
            if(attributeNode.tagName == "id")
                attributeNode.dataContext = Integer.toString(currentNode.id);
            else
                attributeNode.assignRandomValue();
        }
    }

    public void clearContext() {
        maxId = 0;
        contextNodeNameGenerator = new BaseCharIDStyleNameGeneratorImpl('A');
        attributeNodeNameGenerator = new BaseCharIDStyleNameGeneratorImpl('a');
        attributeTemplateGenerator = new AttributeTemplateGeneratorImpl(attributeNodeNameGenerator);
        contextTemplateGenerator = new ContextTemplateGeneratorImpl(contextNodeNameGenerator,
                        attributeTemplateGenerator);
        for(XMLDatatype datatype : XMLDatatype.values()) {
            if(datatype.getValueHandler() != null)
                datatype.getValueHandler().clear();
        }
    }
}
