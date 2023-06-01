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

@FunctionV3
public class ReplaceFunctionNode extends InformationTreeFunctionNode {
    private String internalStr = null;

    public ReplaceFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.STRING;
        functionExpr = "replace";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String subString = GlobalRandom.getInstance().nextSubstring(childList.get(0).getContext().context);
        internalStr = subString;
        fillContentParametersWithStr(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String subString = XMLDatatype.STRING.getValueHandler().getValue(false);
        internalStr = subString;
        fillContentParametersWithStr(childNode);
    }

    private void fillContentParametersWithStr(InformationTreeNode childNode) {
        if(internalStr.length() == 0) internalStr = "a";
        childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, internalStr.replaceAll("[{}]", "e")));
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.6) {
            childList.add(new InformationTreeConstantNode(XMLDatatype.STRING,
                    XMLDatatype.STRING.getValueHandler().getValue(false).replaceAll("[${}]", "c")));
        } else {
            if(prob < 0.9) childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, "$1"));
            else {
                int num = GlobalRandom.getInstance().nextInt(10) + 1;
                childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, "$" + num));
            }
        }
        internalStr = null;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }


    @Override
    public ReplaceFunctionNode newInstance() {
        return new ReplaceFunctionNode();
    }
}
