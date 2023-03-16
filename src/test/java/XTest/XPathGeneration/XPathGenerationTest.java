package XTest.XPathGeneration;

import XTest.DatabaseExecutor.BaseXExecutor;
import XTest.DatabaseExecutor.MainExecutor;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
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
    void XPathGenerationTest() throws IOException, SQLException, XMLDBException, SaxonApiException, MismatchingResultException, InstantiationException, IllegalAccessException, UnexpectedExceptionThrownException {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(4);
        //System.out.println(xmlContext.getXmlContent());
        BaseXExecutor baseXExecutor = BaseXExecutor.getInstance();
        MainExecutor mainExecutor = new MainExecutor();
        XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
        mainExecutor.registerDatabase(baseXExecutor,"BaseX");
        List<Integer> generationDepth = Arrays.asList(5, 6, 3);
        List<String> XPath = new ArrayList<>();
        try {
            mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
            for(Integer depth : generationDepth)
                XPath.add(XPathGenerator.getXPath(depth));
            for(String XPathStr: XPath) {
                System.out.println("Generated XPath: ------------------------------");
                System.out.println(XPathStr);
                System.out.println("Execution Result: =============================");
                System.out.println(mainExecutor.executeAndCompare(XPathStr));
            }
        }finally {
            mainExecutor.close();
        }
    }
}
