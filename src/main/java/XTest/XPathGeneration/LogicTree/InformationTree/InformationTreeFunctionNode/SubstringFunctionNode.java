package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.Pair;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

@FunctionV1
public class SubstringFunctionNode extends InformationTreeFunctionNode {
    private Integer internalLength = null;

    public SubstringFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "substring";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        if(!childNode.checkValidCalculableContext()) {
            fillContentParametersRandom(childNode);
            return;
        }
        internalLength = childNode.getContext().context.length();
        fillContentParametersWithGivenLength(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        if(internalLength == null) {
            internalLength = 20;
        }
        fillContentParametersWithGivenLength(childNode);
    }

    private void fillContentParametersWithGivenLength(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(internalLength == 0) internalLength = 20;
        Pair interval = GlobalRandom.getInstance().nextInterval(internalLength);
        InformationTreeConstantNode constantNodeStart = new InformationTreeConstantNode
                (XMLDatatype.INTEGER, Integer.toString(interval.x));
        InformationTreeConstantNode constantNodeLength = new InformationTreeConstantNode
                (XMLDatatype.INTEGER, Integer.toString(interval.y - interval.x + 1));
        childList.add(constantNodeStart);
        if(prob < 0.5)
            childList.add(constantNodeLength);
        internalLength = null;
    }

    public void fillContentsWithGivenLength(InformationTreeNode childNode, int length) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        internalLength = length;
        fillContentsRandom(childNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }


    @Override
    public SubstringFunctionNode newInstance() {
        return new SubstringFunctionNode();
    }
}
