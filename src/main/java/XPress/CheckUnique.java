package XPress;

import XPress.DatabaseExecutor.BaseXExecutor;
import XPress.DatabaseExecutor.DatabaseExecutor;
import XPress.DatabaseExecutor.MainExecutor;
import XPress.DatabaseExecutor.SaxonExecutor;
import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.ReportGeneration.ReportManager;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.TestException.UnsupportedContextSetUpException;
import XPress.TestRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CheckUnique {
    public static void main(String[] args) throws IOException, SQLException, UnexpectedExceptionThrownException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0] + "/config.txt"));
        String outputFileAddr = reader.readLine();
        BufferedReader testCaseReader = new BufferedReader(new FileReader(reader.readLine()));
        String line;
        List<Boolean> resultList = new ArrayList<>();

        ReportManager reportManager = new ReportManager("dummy.txt");
        MainExecutor mainExecutor = new MainExecutor(reportManager);
        mainExecutor.setReportLock();
        List<DatabaseExecutor> dbExecuterList = new ArrayList<>();
        dbExecuterList.add(BaseXExecutor.getInstance());
        dbExecuterList.add(SaxonExecutor.getInstance());
        for(DatabaseExecutor dbExecutor: dbExecuterList)
            dbExecutor.registerDatabase(mainExecutor);
        XMLDatatype.getCastable(mainExecutor);
        TestRunner testRunner = new TestRunner(mainExecutor);
        boolean flag = false;
        try {
            while ((line = testCaseReader.readLine()) != null) {
                if (line.startsWith("<")) {
                    if (flag)
                        testRunner.clearContext();
                    flag = true;
                    testRunner.setContext(line);
                } else {
                    List<Integer> answer = mainExecutor.executeAndCompareResultOnly(line, true);
                    boolean result = (answer == null);
                    resultList.add(result);
                }
            }
        } catch(Exception e) {
            System.out.println("Error occurred for: " + outputFileAddr);
            testRunner.endTest();
            mainExecutor.close();
            reportManager.close();
        }
        FileWriter writer = new FileWriter(outputFileAddr);
        for(Boolean b: resultList) {
            writer.write((b ? "1" : "0") + "\n");
        }
        writer.close();
    }
}
