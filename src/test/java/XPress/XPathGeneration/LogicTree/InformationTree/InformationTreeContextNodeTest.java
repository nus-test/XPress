package XPress.XPathGeneration.LogicTree.InformationTree;

import XPress.PrimitiveDatatype.XMLDatatype;
import org.junit.jupiter.api.Test;

public class InformationTreeContextNodeTest {

    @Test
    void setXMLDataComplexRecorderIntegrateTest() {
        InformationTreeContextNode contextNode = new InformationTreeContextNode();
        contextNode.datatypeRecorder.setData(XMLDatatype.SEQUENCE, null, false);
        System.out.println(contextNode.datatypeRecorder.xmlDatatype);
    }
}
