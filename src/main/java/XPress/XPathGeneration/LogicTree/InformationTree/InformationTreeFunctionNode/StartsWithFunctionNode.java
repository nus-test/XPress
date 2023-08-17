package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.GlobalRandom;
import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

@FunctionV1
public class StartsWithFunctionNode extends InformationTreeFunctionNode {
    String internalStr = null;

    public StartsWithFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        functionExpr = "starts-with";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        internalStr = childNode.getContext().context;
        if(fillContentParameterBySubRoot(XMLDatatype.STRING)) return;
        fillContentParametersWithGivenContext(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        if(internalStr == null) {
            internalStr = XMLDatatype.STRING.getValueHandler().getValue(false);
        }
        fillContentParametersWithGivenContext(childNode);
    }

    protected void fillContentParametersWithGivenContext(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        while(internalStr.length() == 0) {
            internalStr = XMLDatatype.STRING.getValueHandler().getValue(false);
        }
        int endIndex = GlobalRandom.getInstance().nextInt(internalStr.length()) + 1;
        String endStr = internalStr.substring(0, endIndex);
        if(prob < 0.2) {
            endStr = XMLDatatype.STRING.getValueHandler().mutateValue(endStr);
        }
        childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, endStr));
        internalStr = null;
    }

    public void fillContentsWithGivenContext(InformationTreeNode childNode, String str) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        internalStr = str;
        fillContentsRandom(childNode);
    }

    @Override
    public StartsWithFunctionNode newInstance() {
        return new StartsWithFunctionNode();
    }
    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }
}
