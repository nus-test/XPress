package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public class TextFunctionNode extends InformationTreeDirectContentFunctionNode {
    public TextFunctionNode() {
        functionExpr = "text";
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
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
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        Boolean checkResult = recorder.xmlDatatype == XMLDatatype.NODE;
        if(!recorder.nodeMix) {
            if (!checkResult) {
                if (recorder.xmlDatatype == XMLDatatype.SEQUENCE && recorder.subDatatype == XMLDatatype.NODE)
                    checkResult = true;
            }
        } else checkResult = false;
        return checkResult;
    }

    @Override
    public TextFunctionNode newInstance() {
        return new TextFunctionNode();
    }
}
