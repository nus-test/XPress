package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class HasChildrenFunctionNode extends InformationTreeDirectContentFunctionNode {
    HasChildrenFunctionNode() {
        functionExpr = "has-children";
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        }
        else {
            datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            datatypeRecorder.subDatatype = XMLDatatype.BOOLEAN;
        }
    }

    @Override
    public HasChildrenFunctionNode newInstance() {
        return new HasChildrenFunctionNode();
    }
}
