package XTest.XPathGeneration.PredicateGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.TestException.MismatchingResultException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode.PredicateTreeLogicalConnectionNode;
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

    public PredicateContext generatePredicate(String XPathPrefix, List<ContextNode> candidateNodeList, int maxPhraseLength) throws SQLException, XMLDBException, MismatchingResultException, IOException, SaxonApiException {
        ContextNode randomNode = GlobalRandom.getInstance().getRandomFromList(candidateNodeList);
        PredicateTreeNode currentRoot = null;
        for(int i = 0; i < maxPhraseLength; i ++) {
            PredicateTreeNode singlePhraseNode = generateSinglePhrase(randomNode);
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

    public PredicateTreeNode generateSinglePhrase(ContextNode currentNode) {
        PredicateTreeConstantNode inputNode = getSubcontextFromContextNode(currentNode);
        PredicateTreeFunctionNode functionNode = generateFunctionExpression(inputNode, 2);
        PredicateTreeNode phraseNode = generateSinglePhraseFromFunction(functionNode);
        return phraseNode;
    }

    public PredicateTreeNode generateSinglePhraseFromFunction(PredicateTreeFunctionNode functionNode) {
        return null;
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

        return null;
    }

    public PredicateTreeConstantNode getSubcontextFromContextNode(ContextNode currentNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.3)
            return new PredicateTreeConstantNode(currentNode);
        return new PredicateTreeConstantNode(GlobalRandom.getInstance().getRandomFromList(currentNode.attributeList));
    }
}
