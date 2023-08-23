package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLQName;
import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

//@FunctionV3
public class NamespaceURIFromQNameFunctionNode extends InformationTreeFunctionNode {
    public NamespaceURIFromQNameFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLString.getInstance();
        functionExpr = "namespace-uri-from-QName";
    }

    @Override
    public NamespaceURIFromQNameFunctionNode newInstance() {
        return new NamespaceURIFromQNameFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLQName;
    }
}
