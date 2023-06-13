package XTest.XPathGeneration;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.ReportGeneration.KnownBugs;
import XTest.TestException.DebugErrorException;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.apache.commons.lang3.tuple.Pair;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XPathGenerator {
    MainExecutor mainExecutor;
    SequenceGenerator sequenceGenerator;
    PrefixQualifier prefixQualifier = new PrefixQualifier();
    PredicateGenerator predicateGenerator;
    public XPathGenerator(MainExecutor mainExecutor) {
        this.mainExecutor = mainExecutor;
        predicateGenerator = new PredicateGenerator(mainExecutor);
        sequenceGenerator = new SequenceGenerator(mainExecutor);
    }

    public Pair<List<Pair<Integer, Integer>>, String> generateXPathSectionDivided
            (Pair<List<Pair<Integer, Integer>>, String> currentList,
             XPathResultListPair starterBuildPair, int depth, boolean complex)
            throws SQLException, IOException, UnexpectedExceptionThrownException, DebugErrorException {
        if(depth == 0) {
            return currentList;
        }
        Pair<Pair<Integer, Integer>, XPathResultListPair> XPathRecord = generateXPathSingleSection(starterBuildPair, complex);
        currentList.getLeft().add(XPathRecord.getLeft());
        return generateXPathSectionDivided(Pair.of(currentList.getLeft(), XPathRecord.getRight().XPath),
                XPathRecord.getRight(), depth - 1, complex);
    }

    public String generateXPath(XPathResultListPair starterBuildPair, int depth, boolean complex)
            throws SQLException, IOException, UnexpectedExceptionThrownException, DebugErrorException {
        if(depth == 0) {
            return starterBuildPair.XPath;
        }
        return generateXPath(generateXPathSingleSection(starterBuildPair, complex).getRight()
                , depth - 1, complex);
    }

    public Pair<Pair<Integer, Integer>, XPathResultListPair> generateXPathSingleSection(
            XPathResultListPair starterBuildPair, boolean complex
    ) throws SQLException, IOException, UnexpectedExceptionThrownException, DebugErrorException {
        int idL, idR;
        XPathResultListPair currentBuildPair = new XPathResultListPair(starterBuildPair);
        // First stage
        List<String> availablePrefixes = prefixQualifier.getPrefixes(starterBuildPair.contextNodeList, !complex);
        if(availablePrefixes.isEmpty()) {
            // Something wrong might be happening?
            return null;
        }

        List<Integer> nodeIdList;
        boolean allowTextContentFlag = false;
        ContextNode randomNode;

        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.7 || GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1
                || starterBuildPair.XPath.length() == 0 || mainExecutor.extraLeafNodeList == null) {
            String prefix = GlobalRandom.getInstance().getRandomFromList(availablePrefixes);
            prob = GlobalRandom.getInstance().nextDouble();
            if (prob < 0.5) {
                if (availablePrefixes.get(0).equals("/")) {
                    prob = GlobalRandom.getInstance().nextDouble();
                    int id = prob < 0.5 ? 0 : 1;
                    prefix = availablePrefixes.get(id);
                }
            }
            if (starterBuildPair.XPath.length() == 0) {
                prefix = "//";
            }
            currentBuildPair.XPath += prefix;
            String tempBuilder = currentBuildPair.XPath + "*";
            nodeIdList = mainExecutor.executeSingleProcessorGetIdList(tempBuilder);
            currentBuildPair.contextNodeList = mainExecutor.getNodeListFromIdList(nodeIdList);

            prob = GlobalRandom.getInstance().nextDouble();
            randomNode = GlobalRandom.getInstance().getRandomFromList(currentBuildPair.contextNodeList);
            idL = currentBuildPair.XPath.length();
            // TODO: Saxon bug
            if (prob < -1 && GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_3)
                currentBuildPair.XPath += "*";
            else {
                currentBuildPair.XPath += randomNode.tagName;
                allowTextContentFlag = true;
            }
        } else {
            int length = GlobalRandom.getInstance().nextInt(5) + 1;
            //  System.out.println("#####" + currentBuildPair.XPath);
            idL = currentBuildPair.XPath.length() + 1;
            currentBuildPair.XPath += "/" + sequenceGenerator.generateNodeSequenceFromContext(length, starterBuildPair.contextNodeList);
        }
        nodeIdList = mainExecutor.executeSingleProcessorGetIdList(currentBuildPair.XPath);
        currentBuildPair.contextNodeList = mainExecutor.getNodeListFromIdList(nodeIdList);
//        if(prob < 0.15 && (!KnownBugs.exist)) {
//            currentBuildPair = indexSearchAttempt(currentBuildPair);
//        }
//        prob = GlobalRandom.getInstance().nextDouble();

        // Debug: currently make sure that predicate is generated.
        prob = 0.1;
        // Predicate application start
//        if(prob < 0.6) {
//            randomNode = GlobalRandom.getInstance().getRandomFromList(currentBuildPair.contextNodeList);
//            PredicateContext predicateContext = predicateGenerator.generatePredicate(currentBuildPair.XPath, 3, randomNode,
//                    allowTextContentFlag, complex);
//            currentBuildPair.XPath += predicateContext.predicate;
//            currentBuildPair.contextNodeList = predicateContext.executionResult;
//        }
        // System.out.println("********************** " + currentBuildPair.XPath);
        if(prob < 0.6) {
            randomNode = GlobalRandom.getInstance().getRandomFromList(currentBuildPair.contextNodeList);
            XPathResultListPair predicateContext = predicateGenerator.generatePredicate(currentBuildPair.XPath, 4, !allowTextContentFlag, randomNode);
            currentBuildPair.XPath += predicateContext.XPath;
            currentBuildPair.contextNodeList = predicateContext.contextNodeList;
        }
        // Predicate application end

//        prob = GlobalRandom.getInstance().nextDouble();
//        if(prob < 0.2 && (!KnownBugs.exist))
//            currentBuildPair = indexSearchAttempt(currentBuildPair);
        idR = currentBuildPair.XPath.length();
        return Pair.of(Pair.of(idL, idR), currentBuildPair);
    }

    public String generateXPath(String currentBuilder, List<ContextNode> currentNodeList, int depth, boolean complex) throws SQLException, IOException, UnexpectedExceptionThrownException, InstantiationException, IllegalAccessException, DebugErrorException {
        return generateXPath(new XPathResultListPair(currentBuilder, currentNodeList), depth, complex);
    }

    public String generateXPath(String currentBuilder, List<ContextNode> currentNodeList, int depth) throws SQLException, MismatchingResultException, IOException, UnexpectedExceptionThrownException, InstantiationException, IllegalAccessException, DebugErrorException {
        return generateXPath(currentBuilder, currentNodeList, depth, true);
    }

    public String getXPath(int depth) throws SQLException, MismatchingResultException, IOException, InstantiationException, IllegalAccessException, UnexpectedExceptionThrownException, DebugErrorException {
        return generateXPath("", null, depth);
    }

    public Pair<List<Pair<Integer, Integer>>, String> getXPathSectionDivided
            (int depth)
            throws SQLException, IOException, UnexpectedExceptionThrownException, DebugErrorException {
        return generateXPathSectionDivided(Pair.of(new ArrayList<>(), ""),
                new XPathResultListPair("", null), depth, true);
    }

    XPathResultListPair indexSearchAttempt(XPathResultListPair buildPair) throws SQLException, UnexpectedExceptionThrownException, IOException {
        String builder = buildPair.XPath;
        List<ContextNode> selectedNodeList = buildPair.contextNodeList;
        int length = selectedNodeList.size();
        int id = 1, cnt = 0;
        List<ContextNode> nodeList = new ArrayList<>();
        List<ContextNode> selectedNodeListToReturn = selectedNodeList;
        while(nodeList.size() == 0 && cnt <= 3) {
            id = GlobalRandom.getInstance().nextInt(length) + 1;
            if(cnt == 3) id = 1;
            nodeList = mainExecutor.executeSingleProcessorGetNodeList(builder + "[" + id + "]", "Saxon");
            cnt += 1;
        }
        if(nodeList.size() != 0) {
            builder += "[" + id + "]";
            selectedNodeListToReturn = nodeList;
        }
        return new XPathResultListPair(builder, selectedNodeListToReturn);
    }
}
