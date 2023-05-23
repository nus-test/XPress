package XTest.XPathGeneration.LogicTree.InformationTree;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeContextNode;
import org.junit.jupiter.api.Test;

public class InformationTreeContextNodeTest {

    @Test
    void setXMLDataComplexRecorderIntegrateTest() {
        InformationTreeContextNode contextNode = new InformationTreeContextNode();
        contextNode.datatypeRecorder.setData(XMLDatatype.SEQUENCE, null, false);
        System.out.println(contextNode.datatypeRecorder.xmlDatatype);
    }
}
