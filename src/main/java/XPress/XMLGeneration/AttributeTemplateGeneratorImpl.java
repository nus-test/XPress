package XPress.XMLGeneration;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;

import java.util.ArrayList;
import java.util.List;

public class AttributeTemplateGeneratorImpl implements AttributeTemplateGenerator {
    NameGenerator nameGenerator;

    AttributeTemplateGeneratorImpl(NameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    @Override
    public List<AttributeNode> generateAttributeTemplate(int templateSize) {
        List<AttributeNode> attributeNodeList = new ArrayList<>();
        AttributeNode attributeID = new AttributeNode();
        attributeID.tagName = "id";
        attributeID.dataType = XMLInteger.getInstance();
        attributeNodeList.add(attributeID);

        for(int i = 1; i < templateSize; i ++) {
            AttributeNode attributeNode = new AttributeNode();
            attributeNode.tagName = nameGenerator.generateName();
            attributeNode.dataType = XMLDatatype.getRandomDataType();
            attributeNodeList.add(attributeNode);
        }
        return attributeNodeList;
    }
}
