package XTest.XPathGeneration.PredicateGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLComparable;
import XTest.TestException.MismatchingResultException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode.PredicateTreeLogicalConnectionNode;
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

    public PredicateContext generatePredicate(String XPathPrefix, List<ContextNode> candidateNodeList, int maxPhraseLength, ContextNode randomNode) throws SQLException, XMLDBException, MismatchingResultException, IOException, SaxonApiException {
        String currentNodePrefix = "//*[@id=\"" + randomNode.id + "\"]/";
        PredicateTreeNode currentRoot = null;
        for(int i = 0; i < maxPhraseLength; i ++) {
            PredicateTreeNode singlePhraseNode = generateSinglePhrase(currentNodePrefix, randomNode);
            if(currentRoot == null)
                currentRoot = singlePhraseNode;
            else {
                currentRoot = joinSinglePhrases(currentRoot, singlePhraseNode);
            }
        }
        String predicate = "[" + currentRoot.toString() + "]";
        System.out.println("predicate! " + predicate);
        List<ContextNode> executionResult = mainExecutor.executeGetNodeList(XPathPrefix + predicate);
        PredicateContext predicateContext = new PredicateContext(predicate, XPathPrefix, executionResult);
        return predicateContext;
    }

    public PredicateTreeNode generateSinglePhrase(String XPathPrefix, ContextNode currentNode) throws SQLException, XMLDBException, IOException, SaxonApiException {
        PredicateTreeConstantNode inputNode = getSubcontextFromContextNode(currentNode);
        PredicateTreeFunctionNode functionNode = generateFunctionExpression(inputNode, 1);
        PredicateTreeNode phraseNode = generateSinglePhraseFromFunction(XPathPrefix, functionNode);
        return phraseNode;
    }

    public PredicateTreeNode generateSinglePhraseFromFunction(String XPathPrefix, PredicateTreeFunctionNode functionNode) throws SQLException, XMLDBException, IOException, SaxonApiException {
        functionNode.getDataContent(XPathPrefix, mainExecutor, "BaseX");
        double prob = GlobalRandom.getInstance().nextDouble();
        PredicateTreeLogicalOperationNode singlePhraseNode = PredicateTreeLogicalOperationNode.getRandomLogicalOperationNode(functionNode.datatype);
        PredicateTreeConstantNode constNode = new PredicateTreeConstantNode(
                functionNode.datatype, functionNode.generateRandomCompareValueFromContent());
        singlePhraseNode.join(functionNode, constNode);
        return singlePhraseNode;
    }

    public PredicateTreeNode joinSinglePhrases(PredicateTreeNode leftChild, PredicateTreeNode rightChild) {
        PredicateTreeLogicalConnectionNode connectionNode = PredicateTreeLogicalConnectionNode.getRandomLogicalConnectionNode();
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
        System.out.println("why? " + inputNode.datatype + " " + functionNode.getClass().getSimpleName());
        functionNode.fillContents(inputNode);
        return functionNode;
    }

    public PredicateTreeConstantNode getSubcontextFromContextNode(ContextNode currentNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.3)
            return new PredicateTreeConstantNode(currentNode);
        return new PredicateTreeConstantNode(GlobalRandom.getInstance().getRandomFromList(currentNode.attributeList));
    }
}
