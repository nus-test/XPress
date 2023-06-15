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
             XPathResultListPair starterBuildPair, int depth)
            throws SQLException, IOException, UnexpectedExceptionThrownException, DebugErrorException {
        if(depth == 0) {
            return currentList;
        }
        Pair<Pair<Integer, Integer>, XPathResultListPair> XPathRecord = generateXPathSingleSection(starterBuildPair);
        currentList.getLeft().add(XPathRecord.getLeft());
        if(XPathRecord.getRight().contextNodeList.isEmpty() && GlobalSettings.starNodeSelection && !GlobalSettings.rectifySelection)
            return Pair.of(currentList.getLeft(), XPathRecord.getRight().XPath);
        return generateXPathSectionDivided(Pair.of(currentList.getLeft(), XPathRecord.getRight().XPath),
                XPathRecord.getRight(), depth - 1);
    }

    public String generateXPath(XPathResultListPair starterBuildPair, int depth)
            throws SQLException, IOException, UnexpectedExceptionThrownException, DebugErrorException {
        if(depth == 0) {
            return starterBuildPair.XPath;
        }
        XPathResultListPair XPathRecord = generateXPathSingleSection(starterBuildPair).getRight();
        if(XPathRecord.contextNodeList.isEmpty() && GlobalSettings.starNodeSelection && !GlobalSettings.rectifySelection)
            return XPathRecord.XPath;
        return generateXPath(XPathRecord, depth - 1);
    }

    public Pair<Pair<Integer, Integer>, XPathResultListPair> generateXPathSingleSection(
            XPathResultListPair starterBuildPair
    ) throws SQLException, IOException, UnexpectedExceptionThrownException, DebugErrorException {
        int idL, idR;
        XPathResultListPair currentBuildPair = new XPathResultListPair(starterBuildPair);
        List<String> availablePrefixes = prefixQualifier.getPrefixes(starterBuildPair.contextNodeList);
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
            if(!GlobalSettings.starNodeSelection || currentBuildPair.contextNodeList.isEmpty())
                randomNode = mainExecutor.contextNodeMap.get(
                        GlobalRandom.getInstance().nextInt(mainExecutor.maxId - 1) + 1);
            else randomNode = GlobalRandom.getInstance().getRandomFromList(currentBuildPair.contextNodeList);
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
            idL = currentBuildPair.XPath.length() + 1;
            currentBuildPair.XPath += "/" + sequenceGenerator.generateNodeSequenceFromContext(length, starterBuildPair.contextNodeList);
        }
        nodeIdList = mainExecutor.executeSingleProcessorGetIdList(currentBuildPair.XPath);
        currentBuildPair.contextNodeList = mainExecutor.getNodeListFromIdList(nodeIdList);
//        if(prob < 0.15 && (!KnownBugs.exist)) {
//            currentBuildPair = indexSearchAttempt(currentBuildPair);
//        }
        // Predicate application start
//        if(prob < 0.6) {
//            randomNode = GlobalRandom.getInstance().getRandomFromList(currentBuildPair.contextNodeList);
//            PredicateContext predicateContext = predicateGenerator.generatePredicate(currentBuildPair.XPath, 3, randomNode,
//                    allowTextContentFlag, complex);
//            currentBuildPair.XPath += predicateContext.predicate;
//            currentBuildPair.contextNodeList = predicateContext.executionResult;
//        }
        // System.out.println("********************** " + currentBuildPair.XPath);
        prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.6) {
            Integer randomId = null;
            if(!GlobalSettings.starNodeSelection) {
                randomNode = mainExecutor.contextNodeMap.get(
                        GlobalRandom.getInstance().nextInt(mainExecutor.maxId - 1) + 1);
                if(!currentBuildPair.contextNodeList.isEmpty())
                    randomId = GlobalRandom.getInstance().getRandomFromList(currentBuildPair.contextNodeList).id;
            }
            else {
                randomNode = GlobalRandom.getInstance().getRandomFromList(currentBuildPair.contextNodeList);
                randomId = randomNode.id;
            }
            XPathResultListPair predicateContext = predicateGenerator.generatePredicate(
                    currentBuildPair.XPath, 4, !allowTextContentFlag, randomNode, randomId);
            currentBuildPair.XPath += predicateContext.XPath;
            currentBuildPair.contextNodeList = predicateContext.contextNodeList;
        }
        // Predicate application end

//        prob = GlobalRandom.getInstance().nextDouble();
//        if(prob < 0.2 && (!KnownBugs.exist))
//            currentBuildPair = indexSearchAttempt(currentBuildPair);
        idR = currentBuildPair.XPath.length();
        //System.out.println("--> " + currentBuildPair.XPath.substring(idL, idR) + " " + currentBuildPair.contextNodeList.size());
        return Pair.of(Pair.of(idL, idR), currentBuildPair);
    }

    public String generateXPath(String currentBuilder, List<ContextNode> currentNodeList, int depth) throws SQLException, IOException, UnexpectedExceptionThrownException, InstantiationException, IllegalAccessException, DebugErrorException {
        return generateXPath(new XPathResultListPair(currentBuilder, currentNodeList), depth);
    }

    public String getXPath(int depth) throws SQLException, MismatchingResultException, IOException, InstantiationException, IllegalAccessException, UnexpectedExceptionThrownException, DebugErrorException {
        return generateXPath("", null, depth);
    }

    public Pair<List<Pair<Integer, Integer>>, String> getXPathSectionDivided
            (int depth)
            throws SQLException, IOException, UnexpectedExceptionThrownException, DebugErrorException {
        return generateXPathSectionDivided(Pair.of(new ArrayList<>(), ""),
                new XPathResultListPair("", null), depth);
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
