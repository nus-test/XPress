package XTest;

import XTest.DatabaseExecutor.*;
import XTest.ReportGeneration.ReportManager;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.XMLContext;
import XTest.XMLGeneration.XMLDocumentGenerator;
import XTest.XPathGeneration.XPathGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, SaxonApiException {
        ReportManager reportManager = new ReportManager("C:\\app\\log\\log.txt");
        MainExecutor mainExecutor = new MainExecutor(reportManager);

        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();

        dbExecuterList.add(BaseXExecutor.getInstance());
        dbExecuterList.add(ExistExecutor.getInstance());
        dbExecuterList.add(SaxonExecutor.getInstance());
//        dbExecuterList.add(OracleExecutor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);
        int round = 50;
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        try {
            for (int i = 0; i < round; i++) {
                xmlDocumentGenerator.clearContext();
                XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(40);
                XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
                List<String> XPath = new ArrayList<>();
                System.out.println(xmlContext.getXmlContent());
                try {
                    int xpathCnt = 25;
                    mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
                    for (int j = 0; j < xpathCnt; j++) {
                        System.out.println("Generated XPath: " + j);
                        try {
                            XPath.add(XPathGenerator.getXPath(GlobalRandom.getInstance().nextInt(7)));
                        } catch (MismatchingResultException | UnexpectedExceptionThrownException e) {
                        }
                    }
                }
                finally {
                    mainExecutor.cleanUp();
                }
            }
        }catch (Exception e) {
            System.out.println(e);
        }
        finally {
            reportManager.close();
            mainExecutor.close();
        }
    }
}
