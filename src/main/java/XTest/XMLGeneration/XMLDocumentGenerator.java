package XTest.XMLGeneration;

import XTest.GlobalRandom;

import java.io.*;
import java.util.List;
import java.util.Random;

public class XMLDocumentGenerator {
    ContextTreeGenerator contextTreeGenerator = new ContextTreeGeneratorImpl();

    NameGenerator contextNodeNameGenerator = new BaseCharIDStyleNameGeneratorImpl('A');
    NameGenerator attributeNodeNameGenerator = new BaseCharIDStyleNameGeneratorImpl('a');
    AttributeTemplateGenerator attributeTemplateGenerator = new AttributeTemplateGeneratorImpl(attributeNodeNameGenerator);
    ContextTemplateGenerator contextTemplateGenerator =
            new ContextTemplateGeneratorImpl(contextNodeNameGenerator,
                    attributeTemplateGenerator);
    List<ContextNode> contextNodeTemplateList;


    XMLDocumentGenerator() {}

    XMLDocumentGenerator(ContextTreeGenerator contextTreeGenerator) {
        this.contextTreeGenerator = contextTreeGenerator;
    }

    String getXMLDocument(int contextNodeSize) {
        ContextNode root = generateXMLDocument(contextNodeSize);
        XMLWriter xmlWriter = new XMLWriter();
        return xmlWriter.writeContext(new String(), root);
    }

    String getXMLDocument(ContextNode root) {
        XMLWriter xmlWriter = new XMLWriter();
        return xmlWriter.writeContext(new String(), root);
    }

    ContextNode generateXMLDocumentSave2Resource(int contextNodeSize, String fileName) throws IOException {
        ContextNode root = generateXMLDocument(contextNodeSize);
        String xmlData = getXMLDocument(root);
        FileWriter writer =
                new FileWriter((this.getClass().getResource(fileName).getPath()));
        writer.write(xmlData);
        System.out.println(xmlData);
        writer.flush();
        writer.close();
        return root;
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
        if(currentNode.dataContext == null) {
            System.out.println(currentNode.dataType);
        }
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
}
