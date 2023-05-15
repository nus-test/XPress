package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class NameFunctionNode extends InformationTreeDirectContentFunctionNode {
    NameFunctionNode() {
        functionExpr = "name";
    }

    @Override
    public NameFunctionNode newInstance() {
        return new NameFunctionNode();
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            dataTypeRecorder.xmlDatatype = XMLDatatype.STRING;
        }
        else {
            dataTypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            dataTypeRecorder.subDatatype = XMLDatatype.STRING;
        }
    }
}
