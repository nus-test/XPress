package XPress.TempTest;

import XPress.CommonUtils;
import XPress.DatabaseExecutor.*;
import XPress.TestException.UnexpectedExceptionThrownException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExistIndexTest {
    static String xmlFile = "test1.xml";
    static String xqueryFile = "xquery.txt";
    static String configFile = "config.xml";
    public static void main(String args[]) throws Exception {
        ExistExecutor existExecutor = new ExistExecutor();
        ExistExecutor existIndexExecutor = new ExistExecutor("test-index", "Exist-index");
        MainExecutor mainExecutor = new MainExecutor(null);

        List<DatabaseExecutor> dbExecutorList = new ArrayList<>();

        dbExecutorList.add(existExecutor);
        dbExecutorList.add(existIndexExecutor);

        for(DatabaseExecutor dbExecutor: dbExecutorList)
            dbExecutor.registerDatabase(mainExecutor);

        String indexContent =
                CommonUtils.readInputStream(
                        new ByteArrayInputStream(MySQLSimple.class.getResourceAsStream("/" + configFile).readAllBytes()));
        existIndexExecutor.setIndexByContent(indexContent);

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
