package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.Pair;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLNumeric;
import XTest.PrimitiveDatatype.XMLSequenceHandler;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateGenerator;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;
import XTest.XPathGeneration.XPathGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubcontextExtractor {
    MainExecutor mainExecutor;
    XPathGenerator xPathGenerator;

    List<String> nodeSequenceTransformationList = Arrays.asList("sort", "reverse", "subsequence");
    List<String> nodeSequenceSelectionList = Arrays.asList("head", "tail", "innermost", "outermost");

    List<String> valueSequenceAggregationFunctionList = Arrays.asList("sum", "min", "max", "avg");
    String defaultDBName = "Saxon";

    public SubcontextExtractor(MainExecutor mainExecutor, XPathGenerator xPathGenerator) {
        this.mainExecutor = mainExecutor;
        this.xPathGenerator = xPathGenerator;
    }

    public PredicateTreeConstantNode extractSubcontext(String XPathPrefix, ContextNode currentNode,
                                               boolean allowTextContentFlag, boolean complex)
            throws SQLException, XMLDBException, MismatchingResultException, UnexpectedExceptionThrownException, IOException, SaxonApiException, InstantiationException, IllegalAccessException {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5 || complex == false) {
            return XMLDirectSubcontext.getDirectSubContext(XPathPrefix, mainExecutor, currentNode, allowTextContentFlag);
        }
        prob = GlobalRandom.getInstance().nextDouble();
        PredicateTreeConstantNode predicateTreeConstantNode = null;
        String XPathExpr;
        boolean valueFormat = false;
        List<ContextNode> selectedNodeList;
        if(prob < 0.3) {
            valueFormat = true;
            selectedNodeList = XMLSequenceHandler.getRandomNodeValue();
            XPathExpr = "(" + selectedNodeList.get(0).dataContext + " to "
                    + selectedNodeList.get(1).dataContext;
        }
        else {
            XPathExpr = xPathGenerator.generateXPath(XPathPrefix,
                    Arrays.asList(currentNode), 2);
            selectedNodeList = mainExecutor.executeSingleProcessorGetNodeList(XPathPrefix + XPathExpr, defaultDBName);
        }
        int sequenceTransformCnt = GlobalRandom.getInstance().nextInt(3);
        XPathExpr = transformSequence(XPathExpr, selectedNodeList.size(), sequenceTransformCnt);
        XPathExpr = selectSequence(XPathExpr, GlobalRandom.getInstance().nextInt(3));
        prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.3 && !valueFormat) {
            selectedNodeList = mainExecutor.executeSingleProcessorGetNodeList(XPathPrefix + XPathExpr, defaultDBName);
            ContextNode starredNode = GlobalRandom.getInstance().getRandomFromList(selectedNodeList);
            PredicateTreeConstantNode directSubContextNode = XMLDirectSubcontext.getDirectSubContext
                    (XPathPrefix + "/" + XPathExpr, mainExecutor, starredNode, allowTextContentFlag);
            directSubContextNode.XPathExpr = XPathExpr + "/" + directSubContextNode.XPathExpr;
            return directSubContextNode;
        }
        else {
            if(!valueFormat) {
                prob = GlobalRandom.getInstance().nextDouble();
                if(prob < 0.5) {
                    selectedNodeList = mainExecutor.executeSingleProcessorGetNodeList(XPathPrefix + XPathExpr, defaultDBName);
                    boolean numericValueFound = false;
                    int tried = 0;
                    ContextNode starredNode;
                    PredicateTreeConstantNode directSubContextNode = null;
                    while(tried <= 3 && !numericValueFound) {
                        starredNode = GlobalRandom.getInstance().getRandomFromList(selectedNodeList);
                        directSubContextNode = XMLDirectSubcontext.getDirectSubContext(starredNode);
                        if(directSubContextNode.datatype.getValueHandler() instanceof XMLNumeric)
                            numericValueFound = true;
                        tried += 1;
                    }
                    if(numericValueFound) {
                        String aggregationFunc = GlobalRandom.getInstance().getRandomFromList(valueSequenceAggregationFunctionList);
                        XPathExpr = aggregationFunc + "(" + XPathExpr + "/" + directSubContextNode.XPathExpr + ")";
                        String result = mainExecutor.executeSingleProcessor(XPathPrefix + XPathExpr, defaultDBName);
                        directSubContextNode.dataContent = result;
                        directSubContextNode.XPathExpr = XPathExpr;
                        return directSubContextNode;
                    }
                }
                XPathExpr = "count(" + XPathExpr + ")";
                String result = mainExecutor.executeSingleProcessor(XPathPrefix + XPathExpr, defaultDBName);
                predicateTreeConstantNode = new PredicateTreeConstantNode(XMLDatatype.INTEGER, result);
            }
        }
        return predicateTreeConstantNode;
    }

    String transformSequence(String XPathExpr, int nodeSize, int depth) {
        if(depth == 0) {
            return XPathExpr;
        }
        int id = GlobalRandom.getInstance().nextInt(nodeSequenceTransformationList.size());
        String function = nodeSequenceTransformationList.get(id);
        if(function.equals("subsequence")) {
            Pair pair = GlobalRandom.getInstance().nextInterval(nodeSize);
            int length = pair.y - pair.x + 1;
            double prob = GlobalRandom.getInstance().nextDouble();
            XPathExpr = function + "(" + XPathExpr + " , " + pair.x;
            if(prob < 0.5)
                XPathExpr += " , " + pair.y;
            XPathExpr += ")";
            nodeSize = length;
        }
        else XPathExpr = nodeSequenceTransformationList.get(id) + "(" + XPathExpr + ")";
        return transformSequence(XPathExpr, nodeSize, depth - 1);
    }

    String selectSequence(String XPathExpr, int depth) {
        if(depth == 0) {
            return XPathExpr;
        }
        int id = GlobalRandom.getInstance().nextInt(nodeSequenceSelectionList.size());
        return nodeSequenceTransformationList.get(id) + "(" +XPathExpr + ")";
    }
}
