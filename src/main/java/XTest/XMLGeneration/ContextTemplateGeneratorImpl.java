package XTest.XMLGeneration;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.min;

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
        List<AttributeNode> attributeTemplateList = attributeTemplateGenerator.GenerateAttributeTemplate(attributeSize);
        List<ContextNode> contextTemplateList = new ArrayList<>();

        for(int i = 0; i < templateSize; i ++) {
            ContextNode contextNode = new ContextNode();
            int attributeTotalNum = min(attributeSize, GlobalRandom.getInstance().nextInt(5) + 1);
            contextNode.addAttribute(attributeTemplateList.get(0));
            List<Integer> attributeIdList = GlobalRandom.getInstance().nextIntListNoRep(attributeTotalNum - 1, attributeSize - 1);
            for(int j = 0; j < attributeTotalNum - 1; j ++)
                attributeIdList.set(j, attributeIdList.get(j) + 1);

            for(int j = 0; j < attributeTotalNum - 1; j ++) {
                AttributeNode attributeNode = new AttributeNode(
                        attributeTemplateList.get(attributeIdList.get(j))
                );
                contextNode.addAttribute(attributeNode);
            }
            contextNode.tagName = contextNodeNameGenerator.GenerateName();
            contextNode.dataType = XMLDatatype.getRandomDataType();
            contextTemplateList.add(contextNode);
        }
        return contextTemplateList;
    }
}
