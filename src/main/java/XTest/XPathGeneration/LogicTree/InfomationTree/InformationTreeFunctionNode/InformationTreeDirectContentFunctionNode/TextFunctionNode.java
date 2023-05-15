package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class TextFunctionNode extends InformationTreeDirectContentFunctionNode {

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        int nodeId;
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            nodeId = Integer.parseInt(childNode.context);
        }
        else {
            // Else is applied to a sequence of nodes
            nodeId = Integer.parseInt(childNode.supplementaryContext);
        }
        ContextNode contextNode = mainExecutor.contextNodeMap.get(nodeId);
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.NODE) {
            dataTypeRecorder.xmlDatatype = contextNode.dataType;
        }
        else {
            dataTypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
            dataTypeRecorder.subDatatype = contextNode.dataType;
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
