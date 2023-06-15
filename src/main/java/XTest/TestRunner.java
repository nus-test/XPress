package XTest;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.TestException.DebugErrorException;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.TestException.UnsupportedContextSetUpException;
import XTest.XMLGeneration.XMLContext;
import XTest.XMLGeneration.XMLDocumentGenerator;
import XTest.XMLGeneration.XMLStructuredDocumentGenerator;
import XTest.XPathGeneration.XPathGenerator;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TestRunner {
    MainExecutor mainExecutor;
    XMLDocumentGenerator xmlDocumentGenerator;

    public TestRunner(MainExecutor mainExecutor) {
        this.mainExecutor = mainExecutor;
        if(GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_3)
            xmlDocumentGenerator = new XMLDocumentGenerator();
        else {
            xmlDocumentGenerator = new XMLStructuredDocumentGenerator();
        }
    }

    public void setContext() throws SQLException, UnsupportedContextSetUpException, IOException {
        mainExecutor.cleanUp();
        xmlDocumentGenerator.clearContext();
        XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(50);
        mainExecutor.maxId = mainExecutor.maxIdContainsLeaf = 0;
        System.out.println(xmlContext.getXmlContent());
        mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
    }

    public void clearContext() throws SQLException, IOException {
        mainExecutor.cleanUp();
    }

    public void testContext(int round) throws SQLException, IOException, DebugErrorException {
        for (int j = 0; j < round; j++) {
            XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
            String XPath = "";
            try {
                Pair<List<Pair<Integer, Integer>>, String> XPathResult = XPathGenerator.getXPathSectionDivided(GlobalRandom.getInstance().nextInt(5) + 2);
                XPath = XPathResult.getRight();
                mainExecutor.executeAndCompare(XPathResult);
            } catch (MismatchingResultException | UnexpectedExceptionThrownException e) {
                XPath = e.toString();
                if(e instanceof UnexpectedExceptionThrownException)
                    XPath = ((UnexpectedExceptionThrownException) e).originalException.toString();
            }
            System.out.println("Generated and tested XPath: " + j + " " + XPath);
        }
    }

    public void textContextPackaged(int round) throws SQLException, IOException {
        try {
            setContext();
            testContext(round);
        }
        catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        finally {
            clearContext();
        }
    }



    public void endTest() {
        try {
            mainExecutor.cleanUp();
        } catch (Exception e) {
            System.out.println("Failed to clean up Main executor");
            System.out.println(e);
        }
    }
}
