package XTest.XPathGeneration.PredicateGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode.NotConnectionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode.PredicateTreeLogicalConnectionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode.EqualOperationNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode.PredicateTreeLogicalOperationNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PredicateGenerator {
    MainExecutor mainExecutor;

    public PredicateGenerator(MainExecutor mainExecutor){
        this.mainExecutor = mainExecutor;
    }

    public PredicateContext generatePredicate(String XPathPrefix, int maxPhraseLength, ContextNode randomNode, boolean allowTextContentFlag)
            throws SQLException, XMLDBException, MismatchingResultException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        String currentNodePrefix = "//*[@id=\"" + randomNode.id + "\"]/";
        PredicateTreeNode currentRoot = null;
        for(int i = 0; i < maxPhraseLength; i ++) {
            PredicateTreeNode singlePhraseNode = generateSinglePhrase(currentNodePrefix, randomNode, allowTextContentFlag);
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

    public PredicateTreeNode generateSinglePhrase(String XPathPrefix, ContextNode currentNode, boolean allowTextContentFlag)
            throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        PredicateTreeConstantNode inputNode = getSubcontextFromContextNode(currentNode, allowTextContentFlag);
        PredicateTreeFunctionNode functionNode = generateFunctionExpression(inputNode, 1);
        PredicateTreeNode phraseNode = generateSinglePhraseFromFunction(XPathPrefix, functionNode);
        return phraseNode;
    }

    public PredicateTreeNode generateSinglePhraseFromFunction(String XPathPrefix, PredicateTreeFunctionNode functionNode) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        functionNode.getDataContent(XPathPrefix, mainExecutor, "BaseX");
        PredicateTreeLogicalOperationNode singlePhraseNode = PredicateTreeLogicalOperationNode.getRandomLogicalOperationNode(functionNode.datatype);
        PredicateTreeConstantNode constNode = getConstantNodeFromContent(functionNode);
        singlePhraseNode.join(functionNode, constNode);
        String executionResult = mainExecutor.executeSingleProcessor(XPathPrefix + singlePhraseNode);
        if(executionResult.equals("false")) {
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
        return functionNode;
    }

    public PredicateTreeFunctionNode generateFunctionExpression(PredicateTreeNode inputNode) {
        PredicateTreeFunctionNode functionNode = PredicateTreeFunctionNode.getRandomPredicateTreeFunctionNode(inputNode.datatype);
        functionNode.fillContents(inputNode);
        return functionNode;
    }

    public PredicateTreeConstantNode getSubcontextFromContextNode(ContextNode currentNode, boolean allowTextContentFlag) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.3 && allowTextContentFlag)
            return new PredicateTreeConstantNode(currentNode);
        return new PredicateTreeConstantNode(GlobalRandom.getInstance().getRandomFromList(currentNode.attributeList));
    }
}
