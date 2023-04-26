package XTest.XPathGeneration;

import XTest.DatabaseExecutor.*;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.ContextNode;
import XTest.XMLGeneration.XMLDocumentGenerator;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
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
        PredicateTreeConstantNode predicateTreeConstantNode = sequenceGenerator.generateNodeSequenceFromContext(5, currentContextNodeList);
        System.out.println(predicateTreeConstantNode.dataContent);
    }

    @Test
    void generateStringSequence() {
        PredicateTreeConstantNode predicateTreeConstantNode = sequenceGenerator.generateConstantSequence(5, XMLDatatype.STRING);
        System.out.println(predicateTreeConstantNode.dataContent);
    }

    @Test
    void generateIntegerSequence() {
        PredicateTreeConstantNode predicateTreeConstantNode = sequenceGenerator.generateConstantSequence(5, XMLDatatype.INTEGER);
        System.out.println(predicateTreeConstantNode.dataContent);
    }

    @Test
    void generateDoubleSequence() {
        PredicateTreeConstantNode predicateTreeConstantNode = sequenceGenerator.generateConstantSequence(5, XMLDatatype.DOUBLE);
        System.out.println(predicateTreeConstantNode.dataContent);
    }

    @Test
    void generateBooleanSequence() {
        PredicateTreeConstantNode predicateTreeConstantNode = sequenceGenerator.generateConstantSequence(5, XMLDatatype.BOOLEAN);
        System.out.println(predicateTreeConstantNode.dataContent);
    }
}
