package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLBoolean;
import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.GlobalRandom;
import XPress.DatatypeControl.ValueHandler.XMLStringHandler;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import java.util.Arrays;
import java.util.List;

@FunctionV3
public class ContainsTokenFunctionNode extends InformationTreeFunctionNode {
    ContainsTokenFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLBoolean.getInstance();
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
        else childList.add(new InformationTreeConstantNode(XMLString.getInstance(), GlobalRandom.getInstance().getRandomFromList(subStringList)));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String subString = ((XMLStringHandler) XMLString.getInstance().getValueHandler()).getRandomValue(
                GlobalRandom.getInstance().nextInt(5) + 1);
        childList.add(new InformationTreeConstantNode(XMLString.getInstance(), subString));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLString;
    }
}
