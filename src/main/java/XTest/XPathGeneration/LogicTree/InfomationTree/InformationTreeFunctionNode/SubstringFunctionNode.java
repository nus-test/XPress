package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.Pair;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class SubstringFunctionNode extends InformationTreeFunctionNode {
    private Integer internalLength = null;

    public SubstringFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "substring";
    }

    @Override
    public void fillContentParameters(InformationTreeNode childNode) {
        if(!childNode.checkCalculableContext()) {
            fillContentParametersRandom(childNode);
            return;
        }
        internalLength = childNode.context.length();
        fillContentParametersWithGivenLength(childNode);
    }

    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) {
        if(internalLength == null) {
            internalLength = 20;
        }
        fillContentParametersWithGivenLength(childNode);
    }

    private void fillContentParametersWithGivenLength(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
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

    private void fillContentsWithGivenLength(InformationTreeNode childNode, int length) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        internalLength = length;
        fillContentsRandom(childNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.STRING;
    }


    @Override
    public SubstringFunctionNode newInstance() {
        return new SubstringFunctionNode();
    }
}
