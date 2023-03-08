package XTest.XPathGeneration;

import XTest.DatabaseExecutor.DatabaseExecutor;
import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.TestException.MismatchingResultException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateContext;
import XTest.XPathGeneration.PredicateGeneration.PredicateGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.checkerframework.checker.units.qual.C;
import org.xmldb.api.base.XMLDBException;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XPathGenerator {
    MainExecutor mainExecutor;
    PrefixQualifier prefixQualifier = new PrefixQualifier();
    PredicateGenerator predicateGenerator;
    XPathGenerator(MainExecutor mainExecutor) {
        this.mainExecutor = mainExecutor;
        this.predicateGenerator = new PredicateGenerator(mainExecutor);
    }

    String generateXPath(String currentBuilder, List<ContextNode> currentNodeList, int depth) throws SQLException, XMLDBException, MismatchingResultException, IOException, SaxonApiException{
        if(depth == 0) {
            return currentBuilder;
        }
        String builder = currentBuilder;
        // First stage
        List<String> availablePrefixes = prefixQualifier.getPrefixes(currentNodeList);
        if(availablePrefixes.isEmpty()) {
            // Something wrong might be happening?
            return null;
        }
        String prefix = GlobalRandom.getInstance().getRandomFromList(availablePrefixes);
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) {
            if(availablePrefixes.get(0).equals("/")) {
                prob = GlobalRandom.getInstance().nextDouble();
                int id = prob < 0.5 ? 0 : 1;
                prefix = availablePrefixes.get(id);
            }
        }
        builder += prefix;
        String tempBuilder = builder + "*";
        List<Integer> nodeIdList = mainExecutor.execute(tempBuilder);
        List<ContextNode> selectedNodeList = mainExecutor.getNodeListFromIdList(nodeIdList);
        prob = GlobalRandom.getInstance().nextDouble();
        if(prob > 0.3) {
            PredicateContext predicateContext = predicateGenerator.generatePredicate(builder, selectedNodeList, 3);
            builder += predicateContext.predicate;
            selectedNodeList = predicateContext.executionResult;
        }
        return generateXPath(builder, selectedNodeList, depth - 1);
    }

    String getXPath(int depth) throws SQLException, XMLDBException, MismatchingResultException, IOException, SaxonApiException, InstantiationException, IllegalAccessException {
        return generateXPath("", null, depth);
    }
}
