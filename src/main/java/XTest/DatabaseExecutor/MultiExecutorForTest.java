package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.DatabaseExecutor.*;
import XTest.ReportGeneration.ReportManager;
import XTest.TempTest.MultiTester;
import XTest.TempTest.MySQLSimple;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.TestException.UnsupportedContextSetUpException;
import XTest.XMLGeneration.XMLContext;
import XTest.XMLGeneration.XMLDocumentGenerator;
import XTest.XPathGeneration.XPathGenerator;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MultiExecutorForTest {

    static String xmlFile = "test1.xml";
    static String xqueryFile = "xquery.txt";
    public static void main(String[] args) throws IOException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, SaxonApiException, UnexpectedExceptionThrownException, UnsupportedContextSetUpException {
        MainExecutor mainExecutor = new MainExecutor(null);

        List<DatabaseExecutor> dbExecutorList = new ArrayList<>();

        //dbExecutorList.add(ExistExecutor.getInstance());
        dbExecutorList.add(SaxonExecutor.getInstance());
        dbExecutorList.add(BaseXExecutor.getInstance());

        //dbExecutorList.add(OracleExecutor.getInstance());

        //dbExecutorList.add(LibXML2Executor.getInstance());
        //dbExecutorList.add(PgExecutor.getInstance());
        //dbExecutorList.add(MySQLExecutor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecutorList)
            dbExecutor.registerDatabase(mainExecutor);

        String xmlDataString =
                CommonUtils.readInputStream(
                        new ByteArrayInputStream(MySQLSimple.class.getResourceAsStream("/xmldocs/" + xmlFile).readAllBytes()));
        System.out.println(xmlDataString);

        InputStream inputStream = MultiTester.class.getResourceAsStream("/" + xqueryFile);
        String xquery = CommonUtils.readInputStream(inputStream);

        System.out.println(xquery);

        try {
            mainExecutor.setXPathGenerationContext(xmlDataString);
            for (DatabaseExecutor databaseExecutor : dbExecutorList) {
                System.out.println("------------------------------ " + databaseExecutor.dbName + " ------------------------");
                System.out.println(mainExecutor.executeSingleProcessor(xquery, databaseExecutor));
                System.out.println(mainExecutor.executeSingleProcessorGetIdList(xquery, databaseExecutor));
            }
        } catch (Exception e) {
            if(e instanceof UnexpectedExceptionThrownException)
                System.out.println(((UnexpectedExceptionThrownException) e).originalException);
            System.out.println(e);
        }
        finally {
            mainExecutor.close();
        }

    }
}
