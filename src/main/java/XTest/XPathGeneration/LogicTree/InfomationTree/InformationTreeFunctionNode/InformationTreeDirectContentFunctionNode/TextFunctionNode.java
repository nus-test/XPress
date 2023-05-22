package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class TextFunctionNode extends InformationTreeDirectContentFunctionNode {

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        int nodeId;
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            nodeId = Integer.parseInt(childNode.context);
        }
        else {
            // Else is applied to a sequence of nodes
            nodeId = Integer.parseInt(childNode.supplementaryContext);
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
        if(!checkResult) {
            if(recorder.xmlDatatype == XMLDatatype.SEQUENCE && recorder.subDatatype == XMLDatatype.NODE
                && recorder.nodeMix == false)
                checkResult = true;
        }
        return checkResult;
    }

    @Override
    public TextFunctionNode newInstance() {
        return new TextFunctionNode();
    }
}
