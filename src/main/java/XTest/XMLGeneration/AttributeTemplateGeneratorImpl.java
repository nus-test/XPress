package XTest.XMLGeneration;

import XTest.PrimitiveDatatypes.XMLDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AttributeTemplateGeneratorImpl implements AttributeTemplateGenerator {
    NameGenerator nameGenerator;

    AttributeTemplateGeneratorImpl(NameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    @Override
    public List<AttributeNode> GenerateAttributeTemplate(int templateSize) {
        List<AttributeNode> attributeNodeList = new ArrayList<>();
        AttributeNode attributeID = new AttributeNode();
        attributeID.tagName = "id";
        attributeID.dataType = XMLDataType.INTEGER;
        attributeNodeList.add(attributeID);

        for(int i = 1; i < templateSize; i ++) {
            AttributeNode attributeNode = new AttributeNode();
            attributeNode.tagName = nameGenerator.GenerateName();
            attributeNode.dataType = XMLDataType.getRandomDataType();
        }
        return attributeNodeList;
    }
}
