package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLNumeric;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

// TODO: Should only accept atomic types (could be with implicit cast) and would also result in list of atomic types.
public class DistinctValuesFunctionNode extends InformationTreeSequenceFunctionNode {
    DistinctValuesFunctionNode() {
        functionExpr = "distinct-values";
    }

    @Override
    public DistinctValuesFunctionNode newInstance() {
        return new DistinctValuesFunctionNode();
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        dataTypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
        if(childNode.dataTypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            dataTypeRecorder.subDatatype = childNode.dataTypeRecorder.subDatatype;
        }
        else {
            dataTypeRecorder.xmlDatatype = childNode.dataTypeRecorder.xmlDatatype;
        }
    }
}
