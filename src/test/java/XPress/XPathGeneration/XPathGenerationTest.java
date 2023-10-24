package XPress.XPathGeneration;

import XPress.DatabaseExecutor.*;
import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.GlobalSettings;
import XPress.TestException.DebugErrorException;
import XPress.TestException.MismatchingResultException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.TestException.UnsupportedContextSetUpException;
import XPress.XMLGeneration.XMLContext;
import XPress.XMLGeneration.XMLDocumentGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.junit.jupiter.api.Test;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XPathGenerationTest {

    @Test
    void XPathGeneratorTest() throws SQLException, UnsupportedContextSetUpException, XMLDBException, IOException, SaxonApiException, MismatchingResultException, UnexpectedExceptionThrownException, InstantiationException, IllegalAccessException, DebugErrorException, ClassNotFoundException {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(20);
        System.out.println(xmlContext.getXmlContent());
        MainExecutor mainExecutor = new MainExecutor();
        mainExecutor.setReportLock();
        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();
        GlobalSettings.starNodeSelection = true;
        GlobalSettings.rectifySelection = true;
        dbExecuterList.add(SaxonExecutor.getInstance());
        //dbExecuterList.add(LibXML2Executor.getInstance());
        //dbExecuterList.add(PgExecutor.getInstance());
        //SaxonExecutor.getInstance().compareFlag = false;
        //dbExecuterList.add(new ExistExecutor());
        //dbExecuterList.add(new ExistExecutor("test-index", "Exist-index"));

        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);
        XMLDatatype.getCastable(mainExecutor);

        XPathGeneratorImpl XPathGenerator = new XPathGeneratorImpl(mainExecutor);
        List<String> XPath = new ArrayList<>();
        try {
            mainExecutor.setXPathGenerationContext(xmlContext);
            for(int i = 0; i < 2000; i ++)
                XPath.add(XPathGenerator.getXPath(4));
            for(String XPathStr: XPath) {
                System.out.println("Generated XPath: ------------------------------");
                System.out.println(XPathStr);
                System.out.println("Execution Result: =============================");
                System.out.println(mainExecutor.executeAndCompare(XPathStr));
            }
        } finally {
            mainExecutor.close();
        }
    }

//    @Test
//    void ExistIndexTest() throws SQLException, UnsupportedContextSetUpException, XMLDBException, IOException, SaxonApiException, MismatchingResultException, UnexpectedExceptionThrownException, InstantiationException, IllegalAccessException, DebugErrorException, ClassNotFoundException {
//        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
//        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(20);
//        System.out.println(xmlContext.getXmlContent());
//        MainExecutor mainExecutor = new MainExecutor();
//
//        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();
//
//        dbExecuterList.add(SaxonExecutor.getInstance());
//        SaxonExecutor.getInstance().compareFlag = false;
//        dbExecuterList.add(new ExistExecutor());
//        ExistExecutor existIndexExecutor = new ExistExecutor("test-index", "Exist-index");
//        dbExecuterList.add(existIndexExecutor);
//
//        for(DatabaseExecutor dbExecutor: dbExecuterList)
//            dbExecutor.registerDatabase(mainExecutor);
//        XMLDatatype_t.getCastable(mainExecutor);
//
//        XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
//        List<String> XPath = new ArrayList<>();
//        try {
//            mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
//            List<Pair<String, String>> indexList = mainExecutor.getRandomTagNameTypePair(GlobalRandom.getInstance().nextInt(5) + 1);
//            existIndexExecutor.setIndex(indexList, null);
//            for(int i = 0; i < 300; i ++)
//                XPath.add(XPathGenerator.getXPath(4));
//            for(String XPathStr: XPath) {
//                System.out.println("Generated XPath: ------------------------------");
//                System.out.println(XPathStr);
//                System.out.println("Execution Result: =============================");
//                System.out.println(mainExecutor.executeAndCompare(XPathStr, true));
//            }
//        } finally {
//            mainExecutor.close();
//        }
//    }

//    @Test
//    public void evaluationTest() throws IOException, SQLException, UnexpectedExceptionThrownException, UnsupportedContextSetUpException {
//        GlobalRandom.getInstance().random.setSeed(1686644278934L);
//        Integer setTime = 86400;
//        boolean starNodeSelection = true;
//        boolean rectifySelection = true;
//
//        MainExecutor mainExecutor = new MainExecutor();
//        GlobalSettings.starNodeSelection = starNodeSelection;
//        GlobalSettings.rectifySelection = rectifySelection;
//        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();
//        dbExecuterList.add(SaxonExecutor.getInstance());
//        dbExecuterList.add(BaseXExecutor.getInstance());
//        for(DatabaseExecutor dbExecutor: dbExecuterList)
//            dbExecutor.registerDatabase(mainExecutor);
//        XMLDatatype_t.getCastable(mainExecutor);
//        TestRunner testRunner = new TestRunner(mainExecutor);
//        long start = System.currentTimeMillis();
//
//        System.out.println(setTime);
//        long startWithinHour = start;
//
//        while((System.currentTimeMillis() - start) / 1000 < setTime) {
//            testRunner.setContext();
//            if((System.currentTimeMillis() - startWithinHour) / 1000 > 3600) {
//                startWithinHour = System.currentTimeMillis();
//            }
//            int round = 200;
//            XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
//            for (int j = 0; j < round; j++) {
//                String XPath = "";
//                Pair<List<Pair<Integer, Integer>>, String> XPathResult;
//                try {
//                    XPathResult = XPathGenerator.getXPathSectionDivided(GlobalRandom.getInstance().nextInt(5) + 2);
//                } catch(Exception e) {
//                    continue;
//                }
//                XPath = XPathResult.getRight();
//                if(XPath.contains("null"))
//                    continue;
//                try{
//                    mainExecutor.executeAndCompare(XPath);
//                } catch (MismatchingResultException | UnexpectedExceptionThrownException e) {
//                    if(e instanceof UnexpectedExceptionThrownException && (((UnexpectedExceptionThrownException) e).info.equals(SaxonExecutor.getInstance().dbName))) {
//                        continue;
//                    }
//                    boolean flag = false;
//                    for(int k = XPathResult.getLeft().size() - 1; k >= 0; k --) {
//                        String subPath = XPath.substring(XPathResult.getLeft().get(k).getLeft(),
//                                XPathResult.getLeft().get(k).getRight());
//                        if(subPath.startsWith("(")) {
//                            subPath = "//*/" + subPath;
//                        } else subPath = "//" + subPath;
//                        try {
//                            mainExecutor.executeAndCompare(subPath, false);
//                        } catch (Exception e2) {
//                            if(!flag) {
//                                flag = true;
//                            }
//                        }
//                    }
//                }
//            }
//            testRunner.clearContext();
//        }
//        mainExecutor.close();
//    }
}
