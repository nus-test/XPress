package XPress;

import XPress.DatabaseExecutor.DatabaseExecutor;
import XPress.DatabaseExecutor.MainExecutor;
import XPress.DatabaseExecutor.SaxonExecutor;
import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.DatatypeControl.XMLSimple;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.TestException.UnsupportedContextSetUpException;
import XPress.XMLGeneration.XMLContext;
import XPress.XMLGeneration.XMLDocumentGenerator;
import XPress.XPathGeneration.XPathGenerator;
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
        mainExecutor.setXPathGenerationContext(xmlContext);

        XMLDatatype.getCastable(mainExecutor);

        for(XMLDatatype xmlDatatype : XMLDatatype.allDatatypeList) {
            if(xmlDatatype instanceof XMLSimple) {
                for(XMLDatatype xmlDatatype2 : XMLDatatype.allDatatypeList) {
                    if(xmlDatatype2 instanceof XMLSimple) {
                        System.out.println(xmlDatatype.officialTypeName + " castable as " +
                                xmlDatatype2.officialTypeName + " = " + XMLDatatype.checkCastable(mainExecutor, xmlDatatype, xmlDatatype2));
                    }
                }
            }
        }
    }
}
