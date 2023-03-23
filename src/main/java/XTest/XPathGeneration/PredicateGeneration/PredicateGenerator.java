package XTest.XPathGeneration.PredicateGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.SubstringFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode.NotConnectionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode.PredicateTreeLogicalConnectionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode.EqualOperationNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode.PredicateTreeLogicalOperationNode;
import XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction.SubcontextExtractor;
import XTest.XPathGeneration.XPathGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PredicateGenerator {
    MainExecutor mainExecutor;
    SubcontextExtractor subcontextExtractor;
    XPathGenerator xPathGenerator;

    public PredicateGenerator(MainExecutor mainExecutor, XPathGenerator xPathGenerator){
        this.mainExecutor = mainExecutor;
        this.xPathGenerator = xPathGenerator;
        this.subcontextExtractor = new SubcontextExtractor(mainExecutor, xPathGenerator);
    }

    // XPathPrefix : //*
    public PredicateContext generatePredicate(String XPathPrefix, int maxPhraseLength,
                                              ContextNode randomNode, boolean allowTextContentFlag,
                                              boolean complexFlag)
            throws SQLException, XMLDBException, MismatchingResultException, IOException, SaxonApiException, UnexpectedExceptionThrownException, InstantiationException, IllegalAccessException {
        String currentNodeIdentifier = "[@id=\"" + randomNode.id + "\"]";
        PredicateTreeNode currentRoot = null;
        for(int i = 0; i < maxPhraseLength; i ++) {
            PredicateTreeNode singlePhraseNode = generateSinglePhrase(XPathPrefix, currentNodeIdentifier, randomNode, allowTextContentFlag, complexFlag);
            if(currentRoot == null)
                currentRoot = singlePhraseNode;
            else {
                currentRoot = joinSinglePhrases(currentRoot, singlePhraseNode);
            }
        }
        String predicate = "[" + currentRoot.toString() + "]";
        List<ContextNode> executionResult = mainExecutor.executeGetNodeList(XPathPrefix + predicate);
        PredicateContext predicateContext = new PredicateContext(predicate, XPathPrefix, executionResult);
        return predicateContext;
    }

    public PredicateContext generatePredicate(String XPathPrefix, int maxPhraseLength, ContextNode randomNode, boolean allowTextContentFlag)
            throws SQLException, XMLDBException, MismatchingResultException, IOException, SaxonApiException, UnexpectedExceptionThrownException, InstantiationException, IllegalAccessException {
        return generatePredicate(XPathPrefix, maxPhraseLength, randomNode, allowTextContentFlag, true);
    }

    // XPathPrefix: currentNodePrefix: //*[@id = "1"]/
    public PredicateTreeNode generateSinglePhrase(String XPathPrefixFull, String currentNodeIdentifier, ContextNode currentNode,
                                                  boolean allowTextContentFlag, boolean complexFlag)
            throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException, MismatchingResultException, InstantiationException, IllegalAccessException {
        System.out.println("XPathPrefix: " + XPathPrefixFull);
        System.out.println("selected Node: " + currentNode.id);
        PredicateTreeConstantNode inputNode = getSubcontextFromContextNode(XPathPrefixFull, currentNodeIdentifier, currentNode, allowTextContentFlag, complexFlag);
        PredicateTreeFunctionNode functionNode = generateFunctionExpression(inputNode, 1);
        PredicateTreeNode phraseNode = generateSinglePhraseFromFunction(XPathPrefixFull, currentNodeIdentifier, currentNode, functionNode);
        return phraseNode;
    }

    public PredicateTreeNode generateSinglePhraseFromFunction(String XPathPrefixFull, String currentNodeIdentifier, ContextNode currentNode, PredicateTreeFunctionNode functionNode) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        System.out.println(XPathPrefixFull);
        functionNode.getDataContent(mainExecutor, "BaseX");
        //System.out.println("check function node dataContent", );
        PredicateTreeLogicalOperationNode singlePhraseNode = PredicateTreeLogicalOperationNode.getRandomLogicalOperationNode(functionNode.datatype);
        PredicateTreeConstantNode constNode = getConstantNodeFromContent(functionNode);
        System.out.println("Check! " + functionNode + " " + constNode);
        singlePhraseNode.join(functionNode, constNode);
        List<Integer> executionResult = mainExecutor.executeSingleProcessorGetIdList(XPathPrefixFull + "[" + singlePhraseNode + "]" + currentNodeIdentifier, "BaseX");
        if(!executionResult.contains(currentNode.id)) {
            double prob = GlobalRandom.getInstance().nextDouble();
            if(prob < 0.4) {
                PredicateTreeLogicalConnectionNode notConnectionNode = new NotConnectionNode();
                notConnectionNode.childList.add(singlePhraseNode);
                return notConnectionNode;
            }
            else singlePhraseNode = singlePhraseNode.getOppositeOperationNode();
        }
        return singlePhraseNode;
    }

    public PredicateTreeConstantNode getConstantNodeFromContent(PredicateTreeFunctionNode functionNode) {
        String randomConstantValue = functionNode.generateRandomCompareValueFromContent();
        if(functionNode.datatype == XMLDatatype.BOOLEAN)
            randomConstantValue += "()";
        PredicateTreeConstantNode constNode = new PredicateTreeConstantNode(
                functionNode.datatype, randomConstantValue);
        return constNode;
    }

    public PredicateTreeNode joinSinglePhrases(PredicateTreeNode leftChild, PredicateTreeNode rightChild) {
        PredicateTreeLogicalConnectionNode connectionNode = PredicateTreeLogicalConnectionNode.getRandomBinaryLogicalConnectionNode();
        connectionNode.join(leftChild, rightChild);
        return connectionNode;
    }

    public PredicateTreeFunctionNode generateFunctionExpression(PredicateTreeNode inputNode, int depth) {
        PredicateTreeFunctionNode functionNode = null;
        for(int d = 0; d < depth; d ++) {
            functionNode = generateFunctionExpression(inputNode);
            inputNode = functionNode;
        }
        System.out.println("+++++++++++++++++++++++++++");
        System.out.println("XPathExpr: " + inputNode);
        System.out.println("dataType: " + inputNode.datatype);
        System.out.println("dataContent " + inputNode.dataContent);
        System.out.println("function node " + functionNode.getClass());
        System.out.println("--------------------------");
        return functionNode;
    }

    public PredicateTreeFunctionNode generateFunctionExpression(PredicateTreeNode inputNode) {
        PredicateTreeFunctionNode functionNode = PredicateTreeFunctionNode.getRandomPredicateTreeFunctionNode(inputNode.datatype);
        if(inputNode.datatype == XMLDatatype.STRING && inputNode.dataContent.equals("")) {
            while(functionNode instanceof SubstringFunctionNode)
                functionNode = PredicateTreeFunctionNode.getRandomPredicateTreeFunctionNode(inputNode.datatype);
        }
        functionNode.fillContents(inputNode);
        return functionNode;
    }

    public PredicateTreeConstantNode getSubcontextFromContextNode(String XPathPrefixFull, String currentNodeIdentifier, ContextNode currentNode,
                                                                  boolean allowTextContentFlag, boolean complexFlag)
            throws SQLException, XMLDBException, MismatchingResultException, UnexpectedExceptionThrownException, IOException, SaxonApiException, InstantiationException, IllegalAccessException {
        PredicateTreeConstantNode resultNode = subcontextExtractor.extractSubcontext(XPathPrefixFull, currentNodeIdentifier, currentNode, allowTextContentFlag, complexFlag);
        if(resultNode.dataContent == null) {
            System.out.println("SOS " + resultNode);
        }
        else {
            System.out.println("Prefix: " + XPathPrefixFull);
            System.out.println("For: " + currentNodeIdentifier);
            System.out.println("QAQ " + resultNode);
            System.out.println(resultNode.dataContent);
        }
        return resultNode;
    }
}
