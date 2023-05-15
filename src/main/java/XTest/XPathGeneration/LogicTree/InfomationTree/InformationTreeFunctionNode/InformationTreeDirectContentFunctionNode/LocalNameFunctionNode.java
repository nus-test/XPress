package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class LocalNameFunctionNode extends InformationTreeDirectContentFunctionNode {
    LocalNameFunctionNode() {
        functionExpr = "local-name";
    }

    @Override
    public LocalNameFunctionNode newInstance() {
        return new LocalNameFunctionNode();
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
