package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLNode;
import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class LocalNameFunctionNode extends InformationTreeDirectContentFunctionNode {
    public LocalNameFunctionNode() {
        functionExpr = "local-name";
    }

    @Override
    public LocalNameFunctionNode newInstance() {
        return new LocalNameFunctionNode();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLNode) {
            datatypeRecorder.xmlDatatype = XMLString.getInstance();
        }
        else {
            datatypeRecorder.xmlDatatype = XMLSequence.getInstance();
            datatypeRecorder.subDatatype = XMLString.getInstance();
        }
    }
}
