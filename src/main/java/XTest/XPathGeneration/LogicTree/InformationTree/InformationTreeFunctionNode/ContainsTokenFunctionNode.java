package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLStringHandler;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import java.util.Arrays;
import java.util.List;

@FunctionV3
public class ContainsTokenFunctionNode extends InformationTreeFunctionNode {
    ContainsTokenFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        functionExpr = "contains-token";
    }

    @Override
    public ContainsTokenFunctionNode newInstance() {
        return new ContainsTokenFunctionNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        List<String> subStringList = Arrays.stream(childList.get(0).getContext().context.split("\\s+")).toList();
        if(subStringList.size() == 0)
            fillContentParametersRandom(childNode);
        else childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, GlobalRandom.getInstance().getRandomFromList(subStringList)));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String subString = ((XMLStringHandler)XMLDatatype.STRING.getValueHandler()).getRandomValue(
                GlobalRandom.getInstance().nextInt(5) + 1);
        childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, subString));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING;
    }
}
