package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.Pair;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.PrimitiveDatatype.XMLNumeric;
import XTest.PrimitiveDatatype.XMLSequenceHandler;
import XTest.ReportGeneration.KnownBugs;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.NoActionFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.XPathGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SubcontextExtractor {
    MainExecutor mainExecutor;
    XPathGenerator xPathGenerator;

    List<String> sequenceTransformationList = Arrays.asList("sort", "reverse", "subsequence");
    List<String> sequenceSelectionList = Arrays.asList("head", "tail");
    List<String> nodeSequenceSelectionList = Arrays.asList("innermost", "outermost");
    List<String> valueSequenceSelectionList = Arrays.asList("distinct-values");

    List<String> valueSequenceAggregationFunctionList = Arrays.asList("sum", "min", "max", "avg");
    String defaultDBName = "Saxon";

    public SubcontextExtractor(MainExecutor mainExecutor, XPathGenerator xPathGenerator) {
        this.mainExecutor = mainExecutor;
        this.xPathGenerator = xPathGenerator;
    }

    public PredicateTreeConstantNode extractSubcontext(String XPathPrefixFull, String currentNodeIdentifier, ContextNode currentNode,
                                               boolean allowTextContentFlag, boolean complex)
            throws SQLException, XMLDBException, MismatchingResultException, UnexpectedExceptionThrownException, IOException, SaxonApiException, InstantiationException, IllegalAccessException {
        double prob = GlobalRandom.getInstance().nextDouble();

        String selectCurrentNodeXPath = XPathPrefixFull + currentNodeIdentifier;
        if(prob < 0.3 || !complex || GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1) {
            return XMLDirectSubcontext.getDirectSubContext(XPathPrefixFull, mainExecutor, currentNode, allowTextContentFlag);
        }
        prob = GlobalRandom.getInstance().nextDouble();
        PredicateTreeConstantNode predicateTreeConstantNode = null;
        String XPathExpr;
        boolean valueFormat = false;
        boolean numericValueFound = false;
        XMLDatatype valueDatatype = null;
        List<ContextNode> selectedNodeList;

        String executableXPathExpr;

        if(prob < 0.3) {
            // Return constant integer sequence
            valueFormat = true;
            numericValueFound = true;
            List<Integer> sequenceList = XMLSequenceHandler.getRandomNodeValue();
            XPathExpr = "(" + sequenceList.get(0) + " to "
                    + sequenceList.get(1) + ")";
            executableXPathExpr = XPathExpr;
            predicateTreeConstantNode = new PredicateTreeConstantNode(XMLDatatype.INTEGER, "0", XPathExpr);
            valueDatatype = XMLDatatype.INTEGER;
        }
        else {
            XPathExpr = xPathGenerator.generateXPath(selectCurrentNodeXPath, Arrays.asList(currentNode), 2, false);
            if(XPathExpr == null) {
                return XMLDirectSubcontext.getDirectSubContext(XPathPrefixFull, mainExecutor, currentNode, allowTextContentFlag);
            }
            // XPathExpr: //*[id="4"] //xxxx
            executableXPathExpr = XPathExpr;
            XPathExpr = XPathExpr.substring(selectCurrentNodeXPath.length());
            XPathExpr = "." + XPathExpr;
        }

        //XPathExpr: //*[id="4"] .//xxxx

        int sequenceTransformCnt = GlobalRandom.getInstance().nextInt(3);
        XPathExpr = transformSequence(XPathExpr, executableXPathExpr, sequenceTransformCnt);
        double selectApplicationProb = GlobalRandom.getInstance().nextDouble();
        if(selectApplicationProb < 0.5) {
            XPathExpr = selectSequence(XPathExpr, valueFormat, selectCurrentNodeXPath + "/" + XPathExpr, GlobalRandom.getInstance().nextInt(3));
        }
        // XPathExpr = head(sort(./xxxx))

        if(!valueFormat) {
            prob = GlobalRandom.getInstance().nextDouble();
            if(prob < 0.5) {
                selectedNodeList = mainExecutor.executeSingleProcessorGetNodeList(selectCurrentNodeXPath + "/" + XPathExpr, defaultDBName);
                int tried = 0;
                ContextNode starredNode;
                String savedXPathExpr = XPathExpr;
                while(tried <= 3 && !numericValueFound) {
                    XPathExpr = savedXPathExpr;
                    starredNode = GlobalRandom.getInstance().getRandomFromList(selectedNodeList);
                    boolean currentAllowTextContent = false;
                    predicateTreeConstantNode = XMLDirectSubcontext.getDirectSubContext(selectCurrentNodeXPath + "/" + XPathExpr, mainExecutor,
                            starredNode, currentAllowTextContent);
                    if(predicateTreeConstantNode.datatype.getValueHandler() instanceof XMLNumeric) {
                        numericValueFound = true;
                    }
                    XPathExpr += "/" + predicateTreeConstantNode;
                    valueDatatype = predicateTreeConstantNode.datatype;
                    tried += 1;
                }
                valueFormat = true;
            }
        }
        if(selectApplicationProb > 0.5) {
            XPathExpr = selectSequence(XPathExpr, valueFormat, selectCurrentNodeXPath + "/" + XPathExpr, GlobalRandom.getInstance().nextInt(2));
        }
        double mapProb = GlobalRandom.getInstance().nextDouble();
        if(mapProb < 0.3 && valueFormat) {
            PredicateTreeFunctionNode predicateTreeFunctionNode = PredicateTreeFunctionNode.getRandomPredicateTreeFunctionNode(valueDatatype);
            if(!(predicateTreeFunctionNode instanceof NoActionFunctionNode)) {
                int tot = Integer.parseInt(mainExecutor.executeSingleProcessor("count(" + selectCurrentNodeXPath + "/" + XPathExpr + ")", defaultDBName));
                int selectedIndex = GlobalRandom.getInstance().nextInt(tot) + 1;
                String constantResult = mainExecutor.executeSingleProcessor("string((" + selectCurrentNodeXPath + "/" + XPathExpr + ")[" + selectedIndex + "])", defaultDBName);
                PredicateTreeConstantNode constantNode = new PredicateTreeConstantNode(valueDatatype, constantResult);
                predicateTreeFunctionNode.fillContents(constantNode);
                XPathExpr = "(" + XPathExpr + "!" + predicateTreeFunctionNode.toStringOmit() + ")";
                numericValueFound = predicateTreeFunctionNode.datatype.getValueHandler() instanceof XMLNumeric;
                predicateTreeConstantNode.datatype = predicateTreeFunctionNode.datatype;
            }
        }
        double aggregateProb = GlobalRandom.getInstance().nextDouble();
        if(numericValueFound && aggregateProb < 0.8) {
            String aggregationFunc = GlobalRandom.getInstance().getRandomFromList(valueSequenceAggregationFunctionList);
            XPathExpr = aggregationFunc + "(" + XPathExpr + ")";
            String result = mainExecutor.executeSingleProcessor(selectCurrentNodeXPath + "/" + XPathExpr, defaultDBName);
            if(aggregationFunc.equals("avg"))
                predicateTreeConstantNode.datatype = XMLDatatype.DOUBLE;
            predicateTreeConstantNode.dataContent = predicateTreeConstantNode.datatype == XMLDatatype.INTEGER ?
                    Integer.toString(XMLIntegerHandler.parseInt(result)) : result;
            predicateTreeConstantNode.XPathExpr = XPathExpr;
            return predicateTreeConstantNode;
        }
        XMLDatatype resultType = XMLDatatype.INTEGER;
        XPathExpr = "count(" + XPathExpr + ")";
        String result = mainExecutor.executeSingleProcessor(selectCurrentNodeXPath + "/" + XPathExpr, defaultDBName);
        return new PredicateTreeConstantNode(resultType, result, XPathExpr);
    }

    String transformSequence(String XPathExpr, String executableXPathExpr, int depth) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        if(depth == 0) {
            return XPathExpr;
        }
        Integer nodeSize = Integer.parseInt(mainExecutor.executeSingleProcessor("count(" + executableXPathExpr + ")", defaultDBName));
        int id = GlobalRandom.getInstance().nextInt(sequenceTransformationList.size());
        String function = sequenceTransformationList.get(id);
        if(function.equals("subsequence")) {
            Pair pair = GlobalRandom.getInstance().nextInterval(nodeSize);
            int length = pair.y - pair.x + 1;
            double prob = GlobalRandom.getInstance().nextDouble();
            XPathExpr = function + "(" + XPathExpr + "," + (pair.x + 1);
            executableXPathExpr = function + "(" + executableXPathExpr + "," + (pair.x + 1);
            if(prob < 0.5) {
                XPathExpr += "," + length;
                executableXPathExpr += "," + length;
            }
            XPathExpr += ")";
            executableXPathExpr += ")";
        }
        else {
            XPathExpr = function + "(" + XPathExpr + ")";
            executableXPathExpr = function + "(" + executableXPathExpr + ")";
        }
        return transformSequence(XPathExpr, executableXPathExpr, depth - 1);
    }

    String selectSequence(String XPathExpr, boolean valueFormat, String executableXPathExpr, int depth) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        Integer nodeSize = Integer.parseInt(mainExecutor.executeSingleProcessor("count(" + executableXPathExpr + ")", defaultDBName));
        if(depth == 0) {
            return XPathExpr;
        }
        String function;
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) {
            function = GlobalRandom.getInstance().getRandomFromList(sequenceSelectionList);
            if(function.equals("tail")) {
                if((KnownBugs.exist && KnownBugs.exist4830) || nodeSize == 1) function = "head";
            }
        } else if(valueFormat) {
            function = GlobalRandom.getInstance().getRandomFromList(valueSequenceSelectionList);
        }
        else function = GlobalRandom.getInstance().getRandomFromList(nodeSequenceSelectionList);
        return selectSequence(function + "(" +XPathExpr + ")", valueFormat,
                function + "(" + executableXPathExpr + ")", depth - 1);
    }
}
