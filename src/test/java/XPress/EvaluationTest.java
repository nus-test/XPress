package XPress;

import XPress.Baseline.XPathGeneration.ComXPathGenerator;
import XPress.Baseline.XPathGeneration.XQGenXPathGenerator;
import XPress.DatabaseExecutor.BaseXExecutor;
import XPress.DatabaseExecutor.DatabaseExecutor;
import XPress.DatabaseExecutor.MainExecutor;
import XPress.DatabaseExecutor.SaxonExecutor;
import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.ReportGeneration.KnownBugs;
import XPress.TestException.DebugErrorException;
import XPress.TestException.MismatchingResultException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.TestException.UnsupportedContextSetUpException;
import XPress.XPathGeneration.XPathGenerator;
import XPress.XPathGeneration.XPathGeneratorImpl;
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
    String rootAddr = "e:\\";
    String hourSeparationLine = "-----------------------------\n";
    FileWriter recordWriter;

    public void report(String XPath, String XMLContent) throws IOException {
        if(XMLContent != null) {
            recordWriter.write(XMLContent + "\n");
        }
        recordWriter.write(XPath + "\n");
    }

    @Test
    public void evaluationTest1() throws IOException, SQLException, UnexpectedExceptionThrownException, UnsupportedContextSetUpException, DebugErrorException {
        BufferedReader reader = new BufferedReader(new FileReader(rootAddr + "evaluation_config.txt"));
        GlobalRandom.getInstance().random.setSeed(System.currentTimeMillis());
        Integer setTime = Integer.parseInt(reader.readLine());
        String fileAddr = reader.readLine();
        String recordFileAddr = reader.readLine();
        recordWriter = new FileWriter(recordFileAddr);
        Boolean starNodeSelection = Boolean.parseBoolean(reader.readLine());
        Boolean targetedParameter = Boolean.parseBoolean(reader.readLine());
        Boolean rectifySelection = Boolean.parseBoolean(reader.readLine());
        String generator = reader.readLine();
        Integer generatorId = 0;
        if(generator != null) {
            if(generator.equals("Com"))
                generatorId = 1;
            else if(generator.equals("XQGen"))
                generatorId = 2;
        }
        KnownBugs.basex2213 = true;
        KnownBugs.basex2193 = true;

        MainExecutor mainExecutor = new MainExecutor();
        GlobalSettings.useNamespace = false;
        GlobalSettings.starNodeSelection = starNodeSelection;
        GlobalSettings.targetedSectionPrefix = targetedParameter;
        GlobalSettings.rectifySelection = rectifySelection;
        List<DatabaseExecutor> dbExecutorList = new ArrayList<>();
        dbExecutorList.add(SaxonExecutor.getInstance());
        dbExecutorList.add(BaseXExecutor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecutorList)
            dbExecutor.registerDatabase(mainExecutor);
        XMLDatatype.getCastable(mainExecutor);
        TestRunner testRunner = new TestRunner(mainExecutor);
        long start = System.currentTimeMillis();

        System.out.println(setTime);
        long discrepancyTotal = 0;
        long reducedCnt = 0;
        long totalQueryCnt = 0;
        long sectionLength = 0;
        long discrepancySectionTotal = 0;
        long startWithinHour = start;
        System.out.println((System.currentTimeMillis() - start) / 1000 < setTime);
        while((System.currentTimeMillis() - start) / 1000 < setTime) {
            testRunner.setContext();
            if((System.currentTimeMillis() - startWithinHour) / 1000 > 3600) {
                recordWriter.write(hourSeparationLine);
                startWithinHour = System.currentTimeMillis();
            }
            int round = 200;
            XPathGenerator XPathGenerator;
            if(generatorId == 0)
                XPathGenerator = new XPathGeneratorImpl(mainExecutor);
            else if(generatorId == 1)
                XPathGenerator = new ComXPathGenerator(mainExecutor);
            else
                XPathGenerator = new XQGenXPathGenerator(mainExecutor);
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
                if(XPath.length() == 0) continue;
                if(XPath.contains("null"))
                    continue;
                // if(discrepancyTotal >= 700)
                //     System.out.println(XPath);
                totalQueryCnt ++;
                try{
                    mainExecutor.executeAndCompare(XPath);
                } catch (MismatchingResultException | UnexpectedExceptionThrownException e) {
                    if(e instanceof UnexpectedExceptionThrownException && (((UnexpectedExceptionThrownException) e).info.equals(SaxonExecutor.getInstance().dbName))) {
                        continue;
                    }
                    boolean flag = false;
                    for(int k = XPathResult.getLeft().size() - 1; k >= 0; k --) {
                        String subPath = XPath.substring(XPathResult.getLeft().get(k).getLeft(),
                                XPathResult.getLeft().get(k).getRight());
                        if(subPath.startsWith("(")) {
                            subPath = "//*/" + subPath;
                        } else subPath = "//" + subPath;
                        try {
                            mainExecutor.executeAndCompare(subPath, false);
                        } catch (Exception e2) {
                            if(!flag) {
                                flag = true;
                                reducedCnt ++;
                            }
                            report(subPath, XMLFlag ? null : testRunner.xmlContext);
                            XMLFlag = true;
                            sectionLength += subPath.length();
                            discrepancySectionTotal ++;
                        }
                    }
                    if(!flag) {
                        report(XPath, XMLFlag ? null : testRunner.xmlContext);
                        XMLFlag = true;
                        sectionLength += XPath.length();
                        discrepancySectionTotal ++;
                    }
                    discrepancyTotal ++;
                }
            }
            testRunner.clearContext();
        }
        FileWriter writer = new FileWriter(fileAddr);
        writer.write("evaluation: s:" + GlobalSettings.starNodeSelection + " r:" + GlobalSettings.rectifySelection + "\n");
        writer.write("total-query-cnt:" + totalQueryCnt + "\n");
        writer.write("discrepancy-total:" + discrepancyTotal + "\n");
        writer.write("discrepancy-section-total:" + discrepancySectionTotal + "\n");
        writer.write("reduced-cnt:" + reducedCnt + "\n");
        writer.write("avg-length-after-reduce:" + (discrepancySectionTotal > 0 ? (sectionLength / discrepancySectionTotal) : "NaN") + "\n");
        writer.close();
        recordWriter.close();
        mainExecutor.close();
    }

    @Test
    public void evaluationTest2() throws IOException, SQLException, UnexpectedExceptionThrownException, UnsupportedContextSetUpException {
        BufferedReader reader = new BufferedReader(new FileReader(rootAddr + "evaluation_config.txt"));
        GlobalRandom.getInstance().random.setSeed(System.currentTimeMillis());
        Integer setTime = Integer.parseInt(reader.readLine());
        String fileAddr = reader.readLine();
        Boolean starNodeSelection = Boolean.parseBoolean(reader.readLine());
        Boolean targetedParameter = Boolean.parseBoolean(reader.readLine());
        Boolean rectifySelection = Boolean.parseBoolean(reader.readLine());
        String generator = reader.readLine();
        Integer generatorId = 0;
        if(generator != null) {
            if(generator.equals("Com"))
                generatorId = 1;
            else if(generator.equals("XQGen"))
                generatorId = 2;
        }

        MainExecutor mainExecutor = new MainExecutor();
        GlobalSettings.starNodeSelection = starNodeSelection;
        GlobalSettings.targetedSectionPrefix = targetedParameter;
        GlobalSettings.rectifySelection = rectifySelection;
        GlobalSettings.useNamespace = false;
        List<DatabaseExecutor> dbExecutorList = new ArrayList<>();
        dbExecutorList.add(SaxonExecutor.getInstance());
        dbExecutorList.add(BaseXExecutor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecutorList)
            dbExecutor.registerDatabase(mainExecutor);
        XMLDatatype.getCastable(mainExecutor);
        TestRunner testRunner = new TestRunner(mainExecutor);
        long start = System.currentTimeMillis();

        System.out.println(setTime);
        long startWithinHour = start;


        Integer totalQueryCnt = 0;
        Integer nonEmptyResultSetCount = 0;
        Integer successful = 0;
        Integer SaxonInvalid = 0;
        Integer BaseXInvalid = 0;
        Integer invalid = 0;

        List<Integer> totalQueryCntList = new ArrayList<>();
        List<Integer> nonEmptyResultSetCountList = new ArrayList<>();
        List<Integer> successfulList = new ArrayList<>();
        List<Integer> SaxonInvalidList = new ArrayList<>();
        List<Integer> BaseXInvalidList = new ArrayList<>();
        List<Integer> invalidList = new ArrayList<>();

        while((System.currentTimeMillis() - start) / 1000 < setTime) {
            testRunner.setContext();
            if((System.currentTimeMillis() - startWithinHour) / 1000 > 3600) {
                startWithinHour = System.currentTimeMillis();
                totalQueryCntList.add(totalQueryCnt);
                nonEmptyResultSetCountList.add(nonEmptyResultSetCount);
                successfulList.add(successful);
                SaxonInvalidList.add(SaxonInvalid);
                BaseXInvalidList.add(BaseXInvalid);
                invalidList.add(invalid);
                totalQueryCnt = 0;
                nonEmptyResultSetCount = 0;
                successful = 0;
                SaxonInvalid = 0;
                BaseXInvalid = 0;
                invalid = 0;
            }
            int round = 200;
            XPathGenerator XPathGenerator;
            if(generatorId == 0)
                XPathGenerator = new XPathGeneratorImpl(mainExecutor);
            else if(generatorId == 1)
                XPathGenerator = new ComXPathGenerator(mainExecutor);
            else
                XPathGenerator = new XQGenXPathGenerator(mainExecutor);
            boolean XMLFlag = false;
            for (int j = 0; j < round; j++) {
                String XPath = "";
                Pair<List<Pair<Integer, Integer>>, String> XPathResult;
                try {
                    XPathResult = XPathGenerator.getXPathSectionDivided(GlobalRandom.getInstance().nextInt(6) + 1);
                } catch(Exception e) {
                    continue;
                }
                XPath = XPathResult.getRight();
                if(XPath.contains("null"))
                    continue;
                totalQueryCnt ++;
                try{
                    List<Integer> result = mainExecutor.executeAndCompare(XPath);
                    successful ++;
                    if(result.size() > 0)
                        nonEmptyResultSetCount ++;
                } catch (MismatchingResultException | UnexpectedExceptionThrownException e) {
                    if(e instanceof MismatchingResultException)
                        continue;
                    if(((UnexpectedExceptionThrownException) e).info.equals(SaxonExecutor.getInstance().dbName)) {
                        try {
                            mainExecutor.executeSingleProcessorGetIdList(XPath, BaseXExecutor.getInstance());
                            SaxonInvalid ++;
                        } catch(Exception e2) {
                            invalid ++;
                        }
                    }
                    else {
                        BaseXInvalid ++;
                    }
                }
            }
            testRunner.clearContext();
        }
        if(invalidList.size() < 24) {
            totalQueryCntList.add(totalQueryCnt);
            nonEmptyResultSetCountList.add(nonEmptyResultSetCount);
            successfulList.add(successful);
            SaxonInvalidList.add(SaxonInvalid);
            BaseXInvalidList.add(BaseXInvalid);
            invalidList.add(invalid);
        }
        totalQueryCnt = 0;
        nonEmptyResultSetCount = 0;
        successful = 0;
        SaxonInvalid = 0;
        BaseXInvalid = 0;
        invalid = 0;

        FileWriter writer = new FileWriter(fileAddr);
        writer.write("evaluation: s:" + GlobalSettings.starNodeSelection + " r:" + GlobalSettings.rectifySelection + "\n");
        writer.write("total-query-cnt-list:");
        for(Integer x:totalQueryCntList) {
            totalQueryCnt += x;
            writer.write(" " + x);
        }
        writer.write("\n");
        writer.write("non-empty-list:");
        for(Integer x:nonEmptyResultSetCountList) {
            nonEmptyResultSetCount += x;
            writer.write(" " + x);
        }
        writer.write("\n");
        writer.write("successful-list:");
        for(Integer x:successfulList) {
            successful += x;
            writer.write(" " + x);
        }
        writer.write("\n");
        writer.write("saxon-invalid-list:");
        for(Integer x:SaxonInvalidList) {
            SaxonInvalid += x;
            writer.write(" " + x);
        }
        writer.write("\n");
        writer.write("basex-invalid-list:");
        for(Integer x:BaseXInvalidList) {
            BaseXInvalid += x;
            writer.write(" " + x);
        }
        writer.write("\n");
        writer.write("invalid-list:");
        for(Integer x:invalidList) {
            invalid += x;
            writer.write(" " + x);
        }
        writer.write("\n");

        writer.write("total-query-cnt: " + totalQueryCnt + "\n");
        writer.write("successful: " + successful + "\n");
        writer.write("non-empty: " + nonEmptyResultSetCount + "\n");
        writer.write("invalid: " + invalid + "\n");
        writer.write("saxon-invalid: " + SaxonInvalid + "\n");
        writer.write("basex-invalid: " + BaseXInvalid + "\n");
        writer.close();
        mainExecutor.close();
    }

    
}
