package XTest.XPathGeneration;

import XTest.DatabaseExecutor.*;
import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.ReportGeneration.ReportManager;
import XTest.TestException.DebugErrorException;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.TestException.UnsupportedContextSetUpException;
import XTest.XMLGeneration.XMLContext;
import XTest.XMLGeneration.XMLDocumentGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

        XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
        List<String> XPath = new ArrayList<>();
        try {
            mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
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

    @Test
    void ExistIndexTest() throws SQLException, UnsupportedContextSetUpException, XMLDBException, IOException, SaxonApiException, MismatchingResultException, UnexpectedExceptionThrownException, InstantiationException, IllegalAccessException, DebugErrorException, ClassNotFoundException {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(20);
        System.out.println(xmlContext.getXmlContent());
        MainExecutor mainExecutor = new MainExecutor();

        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();

        dbExecuterList.add(SaxonExecutor.getInstance());
        SaxonExecutor.getInstance().compareFlag = false;
        dbExecuterList.add(new ExistExecutor());
        ExistExecutor existIndexExecutor = new ExistExecutor("test-index", "Exist-index");
        dbExecuterList.add(existIndexExecutor);

        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);
        XMLDatatype.getCastable(mainExecutor);

        XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
        List<String> XPath = new ArrayList<>();
        try {
            mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
            List<Pair<String, String>> indexList = mainExecutor.getRandomTagNameTypePair(GlobalRandom.getInstance().nextInt(5) + 1);
            existIndexExecutor.setIndex(indexList, null);
            for(int i = 0; i < 300; i ++)
                XPath.add(XPathGenerator.getXPath(4));
            for(String XPathStr: XPath) {
                System.out.println("Generated XPath: ------------------------------");
                System.out.println(XPathStr);
                System.out.println("Execution Result: =============================");
                System.out.println(mainExecutor.executeAndCompare(XPathStr, true));
            }
        } finally {
            mainExecutor.close();
        }
    }
}
