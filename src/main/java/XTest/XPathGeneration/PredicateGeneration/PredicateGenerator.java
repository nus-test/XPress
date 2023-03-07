package XTest.XPathGeneration.PredicateGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.TestException.MismatchingResultException;
import XTest.XMLGeneration.ContextNode;
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
        PredicateContextTree predicate = generatePredicate(randomNode, maxPhraseLength);
        boolean found = false;
        List<ContextNode> resultList = null;
        while(found == false) {
            resultList = mainExecutor.executeGetNodeList(XPathPrefix + predicate.toString());
            if(!resultList.isEmpty())
                found = true;
            else predicate = mutatePredicate(randomNode, predicate);
        }
        return new PredicateContext(predicate.toString(), XPathPrefix, resultList);
    }

    public PredicateContextTree generatePredicate(ContextNode currentNode, int maxPhraseLength) {
        for(int i = 0; i < maxPhraseLength; i ++) {
            PredicateContextTree phrase = generatePredicate(currentNode);

        }
        return null;
    }

    public PredicateContextTree generatePredicate(ContextNode currentNode) {
        return null;
    }

    public PredicateContextTree mutatePredicate(ContextNode currentNode, PredicateContextTree basePredicate) {
        return null;
    }
}
