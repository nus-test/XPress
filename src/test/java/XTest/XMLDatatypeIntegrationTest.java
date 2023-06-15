package XTest;

import XTest.DatabaseExecutor.BaseXExecutor;
import XTest.DatabaseExecutor.DatabaseExecutor;
import XTest.DatabaseExecutor.MainExecutor;
import XTest.DatabaseExecutor.SaxonExecutor;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLSimple;
import XTest.ReportGeneration.ReportManager;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.TestException.UnsupportedContextSetUpException;
import XTest.XMLGeneration.XMLContext;
import XTest.XMLGeneration.XMLDocumentGenerator;
import XTest.XPathGeneration.XPathGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.junit.jupiter.api.Test;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XMLDatatypeIntegrationTest {
    @Test
    void getCastableTest() throws SQLException, UnsupportedContextSetUpException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(15);
        System.out.println(xmlContext.getXmlContent());
        MainExecutor mainExecutor = new MainExecutor();
        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();
        dbExecuterList.add(SaxonExecutor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);
        XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
        mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());

        XMLDatatype.getCastable(mainExecutor);

        for(XMLDatatype xmlDatatype : XMLDatatype.values()) {
            if(xmlDatatype.getValueHandler() instanceof XMLSimple) {
                for(XMLDatatype xmlDatatype2 : XMLDatatype.values()) {
                    if(xmlDatatype2.getValueHandler() instanceof XMLSimple) {
                        System.out.println(xmlDatatype.getValueHandler().officialTypeName + " castable as " +
                                xmlDatatype2.getValueHandler().officialTypeName + " = " + XMLDatatype.checkCastable(mainExecutor, xmlDatatype, xmlDatatype2));
                    }
                }
            }
        }
    }
}
