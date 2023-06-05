package XTest;

import XTest.DatabaseExecutor.*;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.ReportGeneration.ReportManager;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.TestException.UnsupportedContextSetUpException;
import XTest.XMLGeneration.XMLContext;
import XTest.XMLGeneration.XMLDocumentGenerator;
import XTest.XMLGeneration.XMLStructuredDocumentGenerator;
import XTest.XPathGeneration.XPathGenerator;
import com.ibm.icu.impl.Pair;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, SaxonApiException, UnexpectedExceptionThrownException {
        ReportManager reportManager = new ReportManager("C:\\app\\log\\log.txt");
        MainExecutor mainExecutor = new MainExecutor(reportManager);

        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();

        dbExecuterList.add(BaseXExecutor.getInstance());
        dbExecuterList.add(SaxonExecutor.getInstance());
//        SaxonExecutor.getInstance().compareFlag = false;
//        dbExecuterList.add(new ExistExecutor());
//        ExistExecutor existIndexExecutor = new ExistExecutor("test-index", "Exist-index");
//        dbExecuterList.add(existIndexExecutor);


   //     dbExecuterList.add(LibXML2Executor.getInstance());
//        dbExecuterList.add(OracleExecutor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);
        XMLDatatype.getCastable(mainExecutor);

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
                XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(50);
                mainExecutor.setExtraLeafNodeContext(xmlDocumentGenerator.generateExtraLeafNodes(15));
                mainExecutor.maxId = 0;
                XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
                System.out.println("------------------ " + i);
                System.out.println(xmlContext.getXmlContent());
                try {
                    int xpathCnt = 200;
                    mainExecutor.setXPathGenerationContext(xmlContext.getRoot(), xmlContext.getXmlContent());
                    List<Pair<String, String>> indexList = mainExecutor.getRandomTagNameTypePair(GlobalRandom.getInstance().nextInt(5) + 1);
                    //existIndexExecutor.setIndex(indexList, null);
                    for (int j = 0; j < xpathCnt; j++) {
//                        if(j == 39) {
//                            GlobalSettings.debugOutput = true;
//                        }
                        String XPath = "";
                        try {
                            XPath = XPathGenerator.getXPath(GlobalRandom.getInstance().nextInt(5) + 2);
                        } catch (MismatchingResultException | UnexpectedExceptionThrownException e) {
                            XPath = e.toString();
                            if(e instanceof UnexpectedExceptionThrownException)
                                XPath = ((UnexpectedExceptionThrownException) e).originalException.toString();
                        }
                        System.out.println("Generated XPath: " + j + " " + XPath);
                    }
                }
                finally {
                    mainExecutor.cleanUp();
                }
            }
        }catch (Exception | UnsupportedContextSetUpException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        finally {
            reportManager.close();
            mainExecutor.close();
        }
    }
}
