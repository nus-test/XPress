package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLMixed;
import XPress.DatatypeControl.PrimitiveDatatype.XMLNode;
import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.XMLGeneration.ContextNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class TextFunctionNode extends InformationTreeDirectContentFunctionNode {
    public TextFunctionNode() {
        functionExpr = "text";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        int nodeId;
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLNode) {
            nodeId = Integer.parseInt(childNode.getContext().context);
        }
        else {
            // Else is applied to a sequence of nodes
            nodeId = Integer.parseInt(childNode.getContext().supplementaryContext);
        }
        ContextNode contextNode = contextInfo.mainExecutor.contextNodeMap.get(nodeId);
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLNode) {
            datatypeRecorder.xmlDatatype = contextNode.dataType;
        }
        else {
            datatypeRecorder.xmlDatatype = XMLSequence.getInstance();
            datatypeRecorder.subDatatype = contextNode.dataType;
        }
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        datatypeRecorder.xmlDatatype = XMLSequence.getInstance();
        datatypeRecorder.subDatatype = XMLMixed.getInstance();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        Boolean checkResult = (childNode.datatypeRecorder.xmlDatatype instanceof XMLNode && !childNode.datatypeRecorder.nodeMix);
        if (!checkResult) {
            if (childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence &&
                    childNode.datatypeRecorder.subDatatype instanceof XMLNode &&
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
