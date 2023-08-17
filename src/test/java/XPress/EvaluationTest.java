package XPress;

import XPress.DatabaseExecutor.BaseXExecutor;
import XPress.DatabaseExecutor.DatabaseExecutor;
import XPress.DatabaseExecutor.MainExecutor;
import XPress.DatabaseExecutor.SaxonExecutor;
import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.TestException.DebugErrorException;
import XPress.TestException.MismatchingResultException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.TestException.UnsupportedContextSetUpException;
import XPress.XPathGeneration.XPathGenerator;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvaluationTest {
    String rootAddr = "";
    FileWriter recordWriter;

    public void report(String XPath, String XMLContent) throws IOException {
        if(XMLContent != null) {
            recordWriter.write(XMLContent);
        }
        recordWriter.write(XPath);
    }

    @Test
    public void coverageTest() throws IOException, SQLException, UnexpectedExceptionThrownException, DebugErrorException, UnsupportedContextSetUpException, InstantiationException, IllegalAccessException {
        BufferedReader reader = new BufferedReader(new FileReader(rootAddr + "basex_config.txt"));
        Integer setTime = Integer.parseInt(reader.readLine());
        String fileAddr = reader.readLine();
        String recordFileAddr = reader.readLine();
        recordWriter = new FileWriter(recordFileAddr);
        Boolean starNodeSelection = Boolean.parseBoolean(reader.readLine());
        Boolean rectifySelection = Boolean.parseBoolean(reader.readLine());

        MainExecutor mainExecutor = new MainExecutor();
        GlobalSettings.starNodeSelection = starNodeSelection;
        GlobalSettings.rectifySelection = rectifySelection;
        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();
        dbExecuterList.add(SaxonExecutor.getInstance());
        dbExecuterList.add(BaseXExecutor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);
        XMLDatatype.getCastable(mainExecutor);
        TestRunner testRunner = new TestRunner(mainExecutor);
        testRunner.setContext();
        long start = System.currentTimeMillis();

        System.out.println(setTime);
        long discrepancyTotal = 0;
        long reducedCnt = 0;
        long totalQueryCnt = 0;
        long sectionLength = 0;

        while((System.currentTimeMillis() - start) / 1000 < setTime) {
            int round = 200;
            XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
            boolean XMLFlag = false;
            for (int j = 0; j < round; j++) {
                String XPath = "";
                Pair<List<Pair<Integer, Integer>>, String> XPathResult;
                try {
                    XPathResult = XPathGenerator.getXPathSectionDivided(GlobalRandom.getInstance().nextInt(5) + 2);
                } catch(Exception e) {
                    continue;
                }
                XPath = XPathResult.getRight();
                if(XPath.contains("null"))
                    continue;
                totalQueryCnt ++;
                try{
                    mainExecutor.executeAndCompare(XPath);
                } catch (MismatchingResultException | UnexpectedExceptionThrownException e) {
                    boolean flag = false;
                    for(int k = 0; k < XPathResult.getLeft().size(); k ++) {
                        String subPath = XPath.substring(XPathResult.getLeft().get(k).getLeft(),
                                XPathResult.getLeft().get(k).getRight());
                        if(subPath.startsWith("(")) {
                            subPath = "//*" + subPath;
                        } else subPath = "//" + subPath;
                        try {
                            mainExecutor.executeAndCompare(subPath, false);
                        } catch (Exception e2) {
                            if(e == e2) {
                                flag = true;
                                report(subPath, XMLFlag ? null : testRunner.xmlContext);
                                sectionLength += subPath.length();
                                reducedCnt ++;
                            }
                        }
                        if(flag) break;
                    }
                    if(!flag) {
                        report(XPath, XMLFlag ? null : testRunner.xmlContext);
                        sectionLength += XPath.length();
                    }
                    discrepancyTotal ++;
                }
            }
        }
        testRunner.clearContext();
        FileWriter writer = new FileWriter(fileAddr);
        writer.write("basex: s:" + GlobalSettings.starNodeSelection + " r:" + GlobalSettings.rectifySelection + "\n");
        writer.write("total-query-cnt:" + totalQueryCnt);
        writer.write("discrepancy-total:" + discrepancyTotal);
        writer.write("reduced-cnt:" + reducedCnt);
        writer.write("avg-length-after-reduce:" + sectionLength / discrepancyTotal);
        writer.close();
    }


    @Test
    public void sectionTest() throws IOException, SQLException, UnexpectedExceptionThrownException, DebugErrorException, UnsupportedContextSetUpException, InstantiationException, IllegalAccessException {
        BufferedReader reader = new BufferedReader(new FileReader(rootAddr + "basex_config.txt"));
        Integer setTime = Integer.parseInt(reader.readLine());
        String fileAddr = reader.readLine();
        String recordFileAddr = reader.readLine();
        Boolean starNodeSelection = Boolean.parseBoolean(reader.readLine());
        Boolean rectifySelection = Boolean.parseBoolean(reader.readLine());

        MainExecutor mainExecutor = new MainExecutor();
        GlobalSettings.starNodeSelection = starNodeSelection;
        GlobalSettings.rectifySelection = rectifySelection;
        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();
        dbExecuterList.add(SaxonExecutor.getInstance());
        dbExecuterList.add(BaseXExecutor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);
        XMLDatatype.getCastable(mainExecutor);
        TestRunner testRunner = new TestRunner(mainExecutor);
        testRunner.setContext();
        long start = System.currentTimeMillis();

        System.out.println(setTime);
        long totalTestedSectionCnt = 0;
        long effectiveSelectionSectionCnt = 0;

        while((System.currentTimeMillis() - start) / 1000 < setTime) {
            int round = 200;
            XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
            for (int j = 0; j < round; j++) {
                String XPath = "";
                Pair<List<Pair<Integer, Integer>>, String> XPathResult;
                try {
                    XPathResult = XPathGenerator.getXPathSectionDivided(GlobalRandom.getInstance().nextInt(5) + 2);
                } catch(Exception e) {
                    continue;
                }
                XPath = XPathResult.getRight();
                if(XPath.contains("null"))
                    continue;
                for(int k = 0; k < XPathResult.getLeft().size(); k ++) {
                    String subPath = XPath.substring(XPathResult.getLeft().get(k).getLeft(),
                            XPathResult.getLeft().get(k).getRight());
                    if(subPath.startsWith("(")) {
                        subPath = "//*" + subPath;
                    } else subPath = "//" + subPath;
                    List<Integer> idListBefore;
                    List<Integer> idListAfter;
                    try {
                        idListAfter = mainExecutor.executeAndCompare(subPath, false);
                    } catch (Exception e2) {
                        break;
                    }
                    int predicateStartIndex = subPath.indexOf("[");
                    String prePath = subPath.substring(predicateStartIndex);
                    try {
                        idListBefore = mainExecutor.executeAndCompare(prePath, false);
                    } catch (Exception e2) {
                        break;
                    }
                    if(idListBefore.size() > 1) {
                        totalTestedSectionCnt ++;
                        if(idListAfter.size() != 0 && idListAfter.size() != idListBefore.size())
                            effectiveSelectionSectionCnt ++;
                    }

                }
            }
        }
        testRunner.clearContext();
        FileWriter writer = new FileWriter(fileAddr);
        writer.write("basex: s:" + GlobalSettings.starNodeSelection + " r:" + GlobalSettings.rectifySelection + "\n");
        writer.close();
    }
}
