package XPress.ReportGeneration;

import XPress.DatabaseExecutor.*;
import XPress.TestException.DebugErrorException;
import XPress.TestException.MismatchingResultException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.TestException.UnsupportedContextSetUpException;
import XPress.XMLGeneration.XMLContext;
import XPress.XMLGeneration.XMLDocumentGenerator;
import XPress.XPathGeneration.XPathGeneratorImpl;
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
    void writeToReportTest() throws IOException, SQLException, XMLDBException, SaxonApiException, MismatchingResultException, InstantiationException, IllegalAccessException, UnexpectedExceptionThrownException, UnsupportedContextSetUpException, DebugErrorException {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(4);
        System.out.println(xmlContext.getXmlContent());
        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();

        dbExecuterList.add(BaseXExecutor.getInstance());
        dbExecuterList.add(SaxonExecutor.getInstance());
        MainExecutor mainExecutor = new MainExecutor();

        XPathGeneratorImpl XPathGenerator = new XPathGeneratorImpl(mainExecutor);
        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);

        ReportManager reportManager = new ReportManager("C:\\app\\log\\log.txt");

        List<Integer> generationDepth = Arrays.asList(5);
        List<String> XPath = new ArrayList<>();
        try {
            mainExecutor.setXPathGenerationContext(xmlContext);
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
