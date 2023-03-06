package XTest.XPathGeneration;

import XTest.DatabaseExecutor.BaseXExecutor;
import XTest.XMLGeneration.XMLContext;
import XTest.XMLGeneration.XMLDocumentGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.junit.jupiter.api.Test;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class XPathGenerationTest {

    @Test
    void XPathGenerationTest() throws IOException, SQLException, XMLDBException, SaxonApiException {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(10);

        BaseXExecutor baseXExecutor = BaseXExecutor.getInstance();
        XPathGenerator xPathGenerator = new XPathGenerator();
        xPathGenerator.registerDatabase(baseXExecutor);
        xPathGenerator.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());

        xPathGenerator.getXPath(5);
    }
}
