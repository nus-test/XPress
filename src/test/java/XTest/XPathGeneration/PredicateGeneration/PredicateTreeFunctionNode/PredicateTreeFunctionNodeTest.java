package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PredicateTreeFunctionNodeTest {
    @Test
    void functionHashMapTest() {
        List<PredicateTreeFunctionNode> predicateTreeFunctionNodeList = PredicateTreeFunctionNode.functionMap.get(XMLDatatype.STRING);
        System.out.println(predicateTreeFunctionNodeList.size());
        for(PredicateTreeFunctionNode a : predicateTreeFunctionNodeList) {
            System.out.println(a.getClass().getSimpleName());
        }
    }
}
