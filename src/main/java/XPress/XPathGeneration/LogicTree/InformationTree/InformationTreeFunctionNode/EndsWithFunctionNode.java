package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLBoolean;
import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.GlobalRandom;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

@FunctionV3
public class EndsWithFunctionNode extends InformationTreeFunctionNode {
    String internalStr = null;

    public EndsWithFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLBoolean.getInstance();
        functionExpr = "ends-with";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        if(fillContentParameterBySubRoot(XMLString.getInstance())) return;
        internalStr = childNode.getContext().context;
        fillContentParametersWithGivenContext(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        if(internalStr == null) {
            internalStr = XMLString.getInstance().getValueHandler().getValue(false);
        }
        fillContentParametersWithGivenContext(childNode);
    }

    public void fillContentsWithGivenContext(InformationTreeNode childNode, String str) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        internalStr = str;
        fillContentsRandom(childNode);
    }

    private void fillContentParametersWithGivenContext(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        while(internalStr.length() == 0) {
            internalStr = XMLString.getInstance().getValueHandler().getValue(false);
        }
        int startIndex = GlobalRandom.getInstance().nextInt(internalStr.length());
        String endStr = internalStr.substring(startIndex);
        if(prob < 0.2) {
            endStr = XMLString.getInstance().getValueHandler().mutateValue(endStr);
        }
        childList.add(new InformationTreeConstantNode(XMLString.getInstance(), endStr));
        internalStr = null;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
    }

    @Override
    public EndsWithFunctionNode newInstance() {
        return new EndsWithFunctionNode();
    }


}
