package XPress.XPathGeneration.LogicTree.InformationTree;

import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import org.junit.jupiter.api.Test;

public class InformationTreeContextNodeTest {

    @Test
    void setXMLDataComplexRecorderIntegrateTest() {
        InformationTreeContextNode contextNode = new InformationTreeContextNode();
        contextNode.datatypeRecorder.setData(XMLSequence.getInstance(), null, false);
        System.out.println(contextNode.datatypeRecorder.xmlDatatype);
    }
}
