package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class TextFunctionNode extends InformationTreeDirectContentFunctionNode {
    public TextFunctionNode() {
        functionExpr = "text";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        int nodeId;
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            nodeId = Integer.parseInt(childNode.getContext().context);
        }
        else {
            // Else is applied to a sequence of nodes
            nodeId = Integer.parseInt(childNode.getContext().supplementaryContext);
        }
        ContextNode contextNode = contextInfo.mainExecutor.contextNodeMap.get(nodeId);
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            datatypeRecorder.xmlDatatype = contextNode.dataType;
        }
        else {
            datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            datatypeRecorder.subDatatype = contextNode.dataType;
        }
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
        datatypeRecorder.subDatatype = XMLDatatype.MIXED;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        Boolean checkResult = (childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE && !childNode.datatypeRecorder.nodeMix);
        if (!checkResult) {
            if (childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE &&
                    childNode.datatypeRecorder.subDatatype == XMLDatatype.NODE &&
                    (!childNode.datatypeRecorder.nodeMix))
                checkResult = true;
        }
        return checkResult;
    }

    @Override
    public TextFunctionNode newInstance() {
        return new TextFunctionNode();
    }
}
