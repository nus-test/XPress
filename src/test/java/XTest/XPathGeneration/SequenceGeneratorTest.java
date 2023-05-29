package XTest.XPathGeneration;

import XTest.DatabaseExecutor.*;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;
import XTest.XMLGeneration.XMLDocumentGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SequenceGeneratorTest {

    List<ContextNode> contextNodeList = new ArrayList<>();
    MainExecutor mainExecutor = new MainExecutor();
    int totalContextNode = 10;
    List<ContextNode> currentContextNodeList = new ArrayList<>();
    SequenceGenerator sequenceGenerator;

    @BeforeEach
    public void init() {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        ContextNode root = xmlDocumentGenerator.generateXMLDocument(totalContextNode);
        getNodes(root);
        List<ContextNode> extraLeafNodes =  xmlDocumentGenerator.generateExtraLeafNodes(10);
        mainExecutor.extraLeafNodeList = extraLeafNodes;

        List<Integer> currentContextIdList = GlobalRandom.getInstance().nextIntListNoRep(
                GlobalRandom.getInstance().nextInt(totalContextNode) + 1, totalContextNode);
        for(Integer id : currentContextIdList)
            currentContextNodeList.add(contextNodeList.get(id));

        sequenceGenerator = new SequenceGenerator(mainExecutor);
    }

    public void getNodes(ContextNode currentNode) {
        contextNodeList.add(currentNode);
        for(ContextNode childNode : currentNode.childList)
            getNodes(childNode);
    }

    @Test
    void generateNodeSequenceFromContext() {
        String nodeSequence = sequenceGenerator.generateNodeSequenceFromContext(5, currentContextNodeList);
        System.out.println(nodeSequence);
    }

    @Test
    void generateStringSequence() {
        System.out.println(sequenceGenerator.generateConstantSequence(5, XMLDatatype.STRING));
    }

    @Test
    void generateIntegerSequence() {
        System.out.println(sequenceGenerator.generateConstantSequence(5, XMLDatatype.INTEGER));
    }

    @Test
    void generateDoubleSequence() {
        System.out.println(sequenceGenerator.generateConstantSequence(5, XMLDatatype.DOUBLE));
    }

    @Test
    void generateBooleanSequence() {
        System.out.println(sequenceGenerator.generateConstantSequence(5, XMLDatatype.BOOLEAN));
    }
}
