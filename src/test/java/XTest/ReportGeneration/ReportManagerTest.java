package XTest.ReportGeneration;

import XTest.DatabaseExecutor.*;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.XMLContext;
import XTest.XMLGeneration.XMLDocumentGenerator;
import XTest.XPathGeneration.XPathGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.junit.jupiter.api.Test;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportManagerTest {

    @Test
    void writeToReportTest() throws IOException, SQLException, XMLDBException, SaxonApiException, MismatchingResultException, InstantiationException, IllegalAccessException, UnexpectedExceptionThrownException {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(4);
        System.out.println(xmlContext.getXmlContent());
        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();

        dbExecuterList.add(BaseXExecutor.getInstance());
        dbExecuterList.add(SaxonExecutor.getInstance());
        MainExecutor mainExecutor = new MainExecutor();

        XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);

        ReportManager reportManager = new ReportManager("C:\\app\\log\\log.txt");

        List<Integer> generationDepth = Arrays.asList(5);
        List<String> XPath = new ArrayList<>();
        try {
            mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
            for(Integer depth : generationDepth)
                XPath.add(XPathGenerator.getXPath(depth));
            for(String XPathStr: XPath) {
                System.out.println("Generated XPath: ------------------------------");
                System.out.println(XPathStr);
                System.out.println("Execution Result: =============================");
                //System.out.println(mainExecutor.executeAndCompare(XPathStr));
                //reportManager.reportInconsistency(mainExecutor, XPathStr.substring(4));
            }
        }finally {
            mainExecutor.close();
            reportManager.close();
        }
    }
}
