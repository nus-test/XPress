package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
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
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        }
        else {
            datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            datatypeRecorder.subDatatype = XMLDatatype.STRING;
        }
    }
}
