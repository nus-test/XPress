package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.DefaultListHashMap;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static XTest.StringUtils.getListString;

public abstract class PredicateTreeFunctionNode extends PredicateTreeNode {
    public static DefaultListHashMap<XMLDatatype, PredicateTreeFunctionNode> functionMap = new DefaultListHashMap<>();

    static {
        PredicateTreeFunctionNode.insertFunctionToMap(new ConcatFunctionNode(), XMLDatatype.STRING);
        PredicateTreeFunctionNode.insertFunctionToMap(new LowerCaseFunctionNode(), XMLDatatype.STRING);
        PredicateTreeFunctionNode.insertFunctionToMap(new UpperCaseFunctionNode(), XMLDatatype.STRING);
        PredicateTreeFunctionNode.insertFunctionToMap(new SubstringFunctionNode(), XMLDatatype.STRING);
        PredicateTreeFunctionNode.insertFunctionToMap(new TranslateFunctionNode(), XMLDatatype.STRING);
        PredicateTreeFunctionNode.insertFunctionToMap(new StringLengthFunctionNode(), XMLDatatype.STRING);
        //PredicateTreeFunctionNode.insertFunctionToMap(new ContainsFunctionNode(), XMLDatatype.STRING);
//        PredicateTreeFunctionNode.insertFunctionToMap(new StartsWithFunctionNode(), XMLDatatype.STRING);
//        PredicateTreeFunctionNode.insertFunctionToMap(new EndsWithFunctionNode(), XMLDatatype.STRING);

        PredicateTreeFunctionNode.insertFunctionToMap(new IntegerAddFunctionNode(), XMLDatatype.INTEGER);
        PredicateTreeFunctionNode.insertFunctionToMap(new IntegerDivisionFunctionNode(), XMLDatatype.INTEGER);
        PredicateTreeFunctionNode.insertFunctionToMap(new IntegerMultiplicationFunctionNode(), XMLDatatype.INTEGER);
        PredicateTreeFunctionNode.insertFunctionToMap(new IntegerSubtractionFunctionNode(), XMLDatatype.INTEGER);
        PredicateTreeFunctionNode.insertFunctionToMap(new IntegerAbsFunctionNode(), XMLDatatype.INTEGER);
        PredicateTreeFunctionNode.insertFunctionToMap(new IntegerModFunctionNode(), XMLDatatype.INTEGER);

        PredicateTreeFunctionNode.insertFunctionToMap(new DoubleAbsFunctionNode(), XMLDatatype.DOUBLE);
        PredicateTreeFunctionNode.insertFunctionToMap(new DoubleSubtractionFunctionNode(), XMLDatatype.DOUBLE);
        PredicateTreeFunctionNode.insertFunctionToMap(new DoubleAddFunctionNode(), XMLDatatype.DOUBLE);
        PredicateTreeFunctionNode.insertFunctionToMap(new DoubleMultiplicationFunctionNode(), XMLDatatype.DOUBLE);
        PredicateTreeFunctionNode.insertFunctionToMap(new DoubleDivisionFunctionNode(), XMLDatatype.DOUBLE);
        PredicateTreeFunctionNode.insertFunctionToMap(new DoubleCeilingFunctionNode(), XMLDatatype.DOUBLE);
        PredicateTreeFunctionNode.insertFunctionToMap(new DoubleRoundFunctionNode(), XMLDatatype.DOUBLE);
        PredicateTreeFunctionNode.insertFunctionToMap(new DoubleRoundHalfToEvenFunctionNode(), XMLDatatype.DOUBLE);
        PredicateTreeFunctionNode.insertFunctionToMap(new DoubleFloorFunctionNode(), XMLDatatype.DOUBLE);
    }

    public static PredicateTreeFunctionNode getRandomPredicateTreeFunctionNode(XMLDatatype datatype) {
        List<PredicateTreeFunctionNode> functionList = functionMap.get(datatype);
        if(functionList != null && functionList.size() > 0)
            return GlobalRandom.getInstance().getRandomFromList(functionList).newInstance();
        return new NoActionFunctionNode();
    }

    static void insertFunctionToMap(PredicateTreeFunctionNode functionNode, XMLDatatype datatype) {
        PredicateTreeFunctionNode.functionMap.get(datatype).add(functionNode);
    }

    public abstract void fillContents(PredicateTreeNode inputNode);

    public void getDataContent(MainExecutor mainExecutor, String databaseName) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        dataContent = mainExecutor.executeSingleProcessor(calculationString(), databaseName);
        if(datatype == XMLDatatype.INTEGER)
            dataContent = XMLIntegerHandler.parseInt(dataContent).toString();
    }

    public abstract PredicateTreeFunctionNode newInstance();
    public String generateRandomCompareValueFromContent() {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5 && this.datatype != XMLDatatype.DOUBLE) return this.dataContent;
        String result =  this.datatype.getValueHandler().mutateValue(this.dataContent);
        return result;
    }

    @Override
    public String toString() {
        return this.XPathExpr + "(" + getListString(childList) + ")";
    }


    public String calculationString() {
        return this.XPathExpr + "(" + getContentListString(childList) + ")";
    }

    public String getContentListString(List<PredicateTreeNode> childList) {
        String resultStr = "";
        boolean needDelim = false;
        for(PredicateTreeNode child: childList) {
            if(needDelim) resultStr += ",";
            resultStr += child.dataContent;
            if(!needDelim) needDelim = true;
        }
        return resultStr;
    }

    public String getContentListOfString(List<PredicateTreeNode> childList) {
        String resultStr = "";
        boolean needDelim = false;
        for(PredicateTreeNode child: childList) {
            if(needDelim) resultStr += ",";
            resultStr += "\"" + child.dataContent + "\"";
            if(!needDelim) needDelim = true;
        }
        return resultStr;
    }

    public static String wrapNumericalBinaryFunctionExpr(PredicateTreeNode childNode, PredicateTreeFunctionNode currentNode) {
        if(childNode instanceof NumericalBinaryOperator && childNode.getClass() != currentNode.getClass()) {
            return "(" + childNode + ")";
        }
        return childNode.toString();
    }
}
