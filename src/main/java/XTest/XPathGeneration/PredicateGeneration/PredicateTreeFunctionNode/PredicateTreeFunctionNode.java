package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.DefaultListHashMap;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class PredicateTreeFunctionNode extends PredicateTreeNode {
    public static DefaultListHashMap<XMLDatatype, PredicateTreeFunctionNode> functionMap = new DefaultListHashMap<>();

    static {
        PredicateTreeFunctionNode.insertFunctionToMap(new ConcatFunctionNode(), XMLDatatype.STRING);
        PredicateTreeFunctionNode.insertFunctionToMap(new IntegerAddFunctionNode(), XMLDatatype.INTEGER);
        PredicateTreeFunctionNode.insertFunctionToMap(new IntegerDivisionFunctionNode(), XMLDatatype.INTEGER);
        PredicateTreeFunctionNode.insertFunctionToMap(new IntegerMultiplicationFunctionNode(), XMLDatatype.INTEGER);
        PredicateTreeFunctionNode.insertFunctionToMap(new IntegerSubtractionFunctionNode(), XMLDatatype.INTEGER);
        PredicateTreeFunctionNode.insertFunctionToMap(new LowerCaseFunctionNode(), XMLDatatype.STRING);
        PredicateTreeFunctionNode.insertFunctionToMap(new UpperCaseFunctionNode(), XMLDatatype.STRING);
    }

    public static PredicateTreeFunctionNode getRandomPredicateTreeFunctionNode(XMLDatatype datatype) {
        List<PredicateTreeFunctionNode> functionList = functionMap.get(datatype);
        double prob = GlobalRandom.getInstance().nextDouble();
        if(functionList != null && functionList.size() > 0 && prob < 0.7)
            return GlobalRandom.getInstance().getRandomFromList(functionList).newInstance();
        return new ExistFunctionNode();
    }

    static void insertFunctionToMap(PredicateTreeFunctionNode functionNode, XMLDatatype datatype) {
        PredicateTreeFunctionNode.functionMap.get(datatype).add(functionNode);
    }

    public abstract void fillContents(PredicateTreeNode inputNode);

    public void getDataContent(String XPathPrefix, MainExecutor mainExecutor, String databaseName) throws SQLException, XMLDBException, IOException, SaxonApiException {
        dataContent = mainExecutor.executeSingleProcessor(XPathPrefix + this, databaseName);
        if(datatype == XMLDatatype.INTEGER)
            dataContent = XMLIntegerHandler.parseInt(dataContent).toString();
    }

    public abstract PredicateTreeFunctionNode newInstance();
    public String generateRandomCompareValueFromContent() {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) return this.dataContent;
        return this.datatype.getValueHandler().mutateValue(this.dataContent);
    }
}
