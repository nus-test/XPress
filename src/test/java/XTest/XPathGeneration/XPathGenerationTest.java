package XTest.XPathGeneration;

import XTest.DatabaseExecutor.BaseXExecutor;
import XTest.DatabaseExecutor.MainExecutor;
import XTest.TestException.MismatchingResultException;
import XTest.XMLGeneration.XMLContext;
import XTest.XMLGeneration.XMLDocumentGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.junit.jupiter.api.Test;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class XPathGenerationTest {

    @Test
    void XPathGenerationTest() throws IOException, SQLException, XMLDBException, SaxonApiException, MismatchingResultException, InstantiationException, IllegalAccessException {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(10);

        BaseXExecutor baseXExecutor = BaseXExecutor.getInstance();
        MainExecutor mainExecutor = new MainExecutor();
        XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
        mainExecutor.registerDatabase(baseXExecutor,"BaseX");
        String XPath;
        try {
            mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
            XPath = XPathGenerator.getXPath(5);
        }finally {
            mainExecutor.close();
        }
        System.out.println("XPath generation result -------------->");
        System.out.println(XPath);
    }
}
