package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class HasChildrenFunctionNode extends InformationTreeDirectContentFunctionNode {
    public HasChildrenFunctionNode() {
        functionExpr = "has-children";
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
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
