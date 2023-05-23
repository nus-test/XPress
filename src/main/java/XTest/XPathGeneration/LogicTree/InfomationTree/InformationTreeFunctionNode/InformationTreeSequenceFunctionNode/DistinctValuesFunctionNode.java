package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

// TODO: Should only accept atomic types (could be with implicit cast) and would also result in list of atomic types.
public class DistinctValuesFunctionNode extends InformationTreeSequenceFunctionNode {
    public DistinctValuesFunctionNode() {
        functionExpr = "distinct-values";
    }

    @Override
    public DistinctValuesFunctionNode newInstance() {
        return new DistinctValuesFunctionNode();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            datatypeRecorder.subDatatype = childNode.datatypeRecorder.subDatatype;
        }
        else {
            datatypeRecorder.xmlDatatype = childNode.datatypeRecorder.xmlDatatype;
        }
    }
}
