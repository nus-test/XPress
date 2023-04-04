package XTest.XPathGeneration;

import XTest.DatabaseExecutor.*;
import XTest.GlobalRandom;
import XTest.ReportGeneration.ReportManager;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.TestException.UnsupportedContextSetUpException;
import XTest.XMLGeneration.XMLContext;
import XTest.XMLGeneration.XMLDocumentGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.junit.jupiter.api.Test;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XPathGenerationTest {

    @Test
    void XPathGenerationTest() throws IOException, SQLException, XMLDBException, SaxonApiException, MismatchingResultException, InstantiationException, IllegalAccessException, UnexpectedExceptionThrownException, ClassNotFoundException, UnsupportedContextSetUpException {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(15);
        System.out.println(xmlContext.getXmlContent());
        ReportManager reportManager = new ReportManager("C:\\app\\log\\log.txt");
        MainExecutor mainExecutor = new MainExecutor(reportManager);

        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();

        dbExecuterList.add(BaseXExecutor.getInstance());
       // dbExecuterList.add(ExistExecutor.getInstance());
        dbExecuterList.add(SaxonExecutor.getInstance());
        dbExecuterList.add(OracleExecutor.getInstance());
        dbExecuterList.add(LibXML2Executor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);

        XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
        List<String> XPath = new ArrayList<>();
        try {
            mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
            for(int i = 0; i < 100; i ++)
                XPath.add(XPathGenerator.getXPath(3));
            for(String XPathStr: XPath) {
                System.out.println("Generated XPath: ------------------------------");
                System.out.println(XPathStr);
                System.out.println("Execution Result: =============================");
                System.out.println(mainExecutor.executeAndCompare(XPathStr));
            }
        }finally {
           reportManager.close();
            mainExecutor.close();
        }
    }
}
