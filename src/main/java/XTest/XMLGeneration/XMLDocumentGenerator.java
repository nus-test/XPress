package XTest.XMLGeneration;

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
    Random random = new Random();


    XMLDocumentGenerator() {}

    XMLDocumentGenerator(ContextTreeGenerator contextTreeGenerator) {
        this.contextTreeGenerator = contextTreeGenerator;
    }

    ContextNode GenerateXMLDocument(int contextNodeSize) {
        ContextNode root = contextTreeGenerator.GenerateRandomTree(contextNodeSize);
        int templateSize = contextNodeSize / 2;
        contextNodeTemplateList = contextTemplateGenerator.GenerateContextTemplate(templateSize);
        assignTemplateToNode(root);
        return root;
    }

    void assignTemplateToNode(ContextNode currentNode) {
        int templateId = random.nextInt(contextNodeTemplateList.size());
        currentNode.assignTemplate(contextNodeTemplateList.get(templateId));
        currentNode.assignRandomValue();
        for(AttributeNode attributeNode: currentNode.attributeList)
            attributeNode.assignRandomValue();
        for(ContextNode childNode: currentNode.childList) {
            assignTemplateToNode(childNode);
        }
    }
}
