package XMLGeneration;

import PrimitiveDatatypes.XMLDataType;
import org.checkerframework.checker.units.qual.A;

import javax.management.AttributeList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContextTemplateGeneratorImpl implements ContextTemplateGenerator {
    NameGenerator contextNodeNameGenerator;
    AttributeTemplateGenerator attributeTemplateGenerator;
    ContextTemplateGeneratorImpl(NameGenerator contextNodeNameGenerator,
                                 AttributeTemplateGenerator attributeTemplateGenerator) {
        this.contextNodeNameGenerator = contextNodeNameGenerator;
        this.attributeTemplateGenerator = attributeTemplateGenerator;
    }

    @Override
    public List<ContextNode> GenerateContextTemplate(int templateSize) {
        int attributeSize = (int) (templateSize * 0.8);
        Random random = new Random();
        List<AttributeNode> attributeTemplateList = attributeTemplateGenerator.GenerateAttributeTemplate(attributeSize);
        List<ContextNode> contextTemplateList = new ArrayList<>();

        for(int i = 0; i < templateSize; i ++) {
            ContextNode contextNode = new ContextNode();
            int attributeTotalNum = random.nextInt(5);
            contextNode.addAttribute(attributeTemplateList.get(0));
            for(int j = 0; j < attributeTotalNum; j ++) {
                int attributeId = random.nextInt(attributeSize - 1) + 1;
                AttributeNode attributeNode = new AttributeNode(
                        attributeTemplateList.get(attributeId)
                );
                contextNode.addAttribute(attributeNode);
            }
            contextNode.tagName = contextNodeNameGenerator.GenerateName();
            contextNode.dataType = XMLDataType.getRandomDataType();
            contextTemplateList.add(contextNode);
        }
        return contextTemplateList;
    }
}
