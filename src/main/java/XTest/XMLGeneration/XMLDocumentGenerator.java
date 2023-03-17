package XTest.XMLGeneration;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;

import java.io.*;
import java.util.List;

public class XMLDocumentGenerator {
    ContextTreeGenerator contextTreeGenerator = new ContextTreeGeneratorImpl();

    NameGenerator contextNodeNameGenerator = new BaseCharIDStyleNameGeneratorImpl('A');
    NameGenerator attributeNodeNameGenerator = new BaseCharIDStyleNameGeneratorImpl('a');
    AttributeTemplateGenerator attributeTemplateGenerator = new AttributeTemplateGeneratorImpl(attributeNodeNameGenerator);
    ContextTemplateGenerator contextTemplateGenerator =
            new ContextTemplateGeneratorImpl(contextNodeNameGenerator,
                    attributeTemplateGenerator);
    List<ContextNode> contextNodeTemplateList;


    public XMLDocumentGenerator() {}

    public XMLDocumentGenerator(ContextTreeGenerator contextTreeGenerator) {
        this.contextTreeGenerator = contextTreeGenerator;
    }

    String getXMLDocument(int contextNodeSize) {
        ContextNode root = generateXMLDocument(contextNodeSize);
        return getXMLDocument(root);
    }

    String getXMLDocument(ContextNode root) {
        XMLWriter xmlWriter = new XMLWriter();
        return xmlWriter.writeContext(new String(), root);
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

    ContextNode generateXMLDocument(int contextNodeSize) {
        ContextNode root = contextTreeGenerator.GenerateRandomTree(contextNodeSize);
        int templateSize = contextNodeSize / 2;
        contextNodeTemplateList = contextTemplateGenerator.GenerateContextTemplate(templateSize);
        assignTemplateToNode(root);
        return root;
    }

    void assignTemplateToNode(ContextNode currentNode) {
        int templateId = GlobalRandom.getInstance().nextInt(contextNodeTemplateList.size());
        currentNode.assignTemplate(contextNodeTemplateList.get(templateId));
        currentNode.assignRandomValue();
        for(AttributeNode attributeNode: currentNode.attributeList) {
            if(attributeNode.tagName == "id")
                attributeNode.dataContext = Integer.toString(currentNode.id);
            else
                attributeNode.assignRandomValue();
        }
        for(ContextNode childNode: currentNode.childList) {
            assignTemplateToNode(childNode);
        }
    }

    public void clearContext() {
        contextNodeNameGenerator = new BaseCharIDStyleNameGeneratorImpl('A');
        attributeNodeNameGenerator = new BaseCharIDStyleNameGeneratorImpl('a');
        for(XMLDatatype datatype : XMLDatatype.values()) {
            datatype.getValueHandler().clear();
        }
    }
}
