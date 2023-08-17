package XPress;

import XPress.DatabaseExecutor.*;
import XPress.TestException.MismatchingResultException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XMLGeneration.XMLContext;
import XPress.XMLGeneration.XMLDocumentGenerator;
import XPress.XMLGeneration.XMLStructuredDocumentGenerator;
import XPress.XPathGeneration.XPathGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestCaseGeneration {
    public static void main(String[] args) throws IOException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, SaxonApiException {
        MainExecutor mainExecutor = new MainExecutor();
        FileWriter testCaseWriter = new FileWriter("C:\\app\\log\\testCases_2_500_100.txt");
        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();
        dbExecuterList.add(SaxonExecutor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);
        int round = 500;
        XMLDocumentGenerator xmlDocumentGenerator;
        if(GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_3)
            xmlDocumentGenerator = new XMLDocumentGenerator();
        else {
            xmlDocumentGenerator = new XMLStructuredDocumentGenerator();
        }
        try {
            for (int i = 0; i < round; i++) {
                xmlDocumentGenerator.clearContext();
                XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(120);
                XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
                testCaseWriter.write(xmlContext.getXmlContent() + "\n");
                System.out.println("Writing round " + i + "...");
                try {
                    int xpathCnt = 100;
                    mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
                    for (int j = 0; j < xpathCnt; j++) {
                        String XPath = "";
                        try {
                            XPath = XPathGenerator.getXPath(GlobalRandom.getInstance().nextInt(3) + 2);
                        } catch (MismatchingResultException | UnexpectedExceptionThrownException e) {
                            XPath = e.toString();
                            if(e instanceof UnexpectedExceptionThrownException)
                                XPath = ((UnexpectedExceptionThrownException) e).originalException.toString();
                        }
                        testCaseWriter.write(XPath + "\n");
                    }
                }
                finally {
                    mainExecutor.cleanUp();
                }
            }
        }catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        finally {
            mainExecutor.close();
            testCaseWriter.close();
        }
    }
}
