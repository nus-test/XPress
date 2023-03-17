package XTest.ReportGeneration;

import XTest.DatabaseExecutor.DatabaseExecutor;
import XTest.DatabaseExecutor.MainExecutor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReportManager {
    String logFilePath;
    BufferedWriter bufferedWriter;
    int id = 0;

    public ReportManager(String logFilePath) throws IOException {
        this.logFilePath = logFilePath;
        Path path = Paths.get(logFilePath);
        File file = new File(logFilePath);
        boolean isFile = file.isFile();
        if(isFile) {
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                if(line.startsWith("id:")) {
                    String idNum = line.substring(3);
                    id = Integer.parseInt(idNum);
                }
            }
        }
        else {
            id = 0;
        }
        bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    void logToFile(String text) throws IOException {
        bufferedWriter.write(text);
    }

    public void close() throws IOException {
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public void reportUnexpectedException(MainExecutor mainExecutor, String XPath, Exception e) throws IOException {
        reportPotentialBug(mainExecutor, XPath);
        logToFile("incaseoriginalxml:" + mainExecutor.currentContext + "\n");
        logToFile("incaseoriginalxpath:" + XPath + "\n");
        logToFile("following incurred unexpected exception of:" + e + "\n");
        reportPotentialBug(mainExecutor, XPath);
    }

    public void reportPotentialBug(MainExecutor mainExecutor, String XPath) throws IOException {
        Map<String, String> answerSetMap = new HashMap<>();
        Map<String, String> errorMessageMap = new HashMap<>();
        id += 1;
        logToFile("id:" + id + "\n");
        logToFile("originalxml:" + mainExecutor.currentContext + "\n");
        logToFile("originalxpath:" + XPath + "\n");
        logToFile("executionresults:\n");
        for(DatabaseExecutor databaseExecutor:mainExecutor.databaseExecutorList) {
            try {
                List<Integer> resultNodeIntegerList = databaseExecutor.executeGetNodeIdList(XPath);
                String resultIdStringExpr = "";
                boolean delim = false;
                for(int id : resultNodeIntegerList) {
                    if(delim) resultIdStringExpr += ",";
                    resultIdStringExpr += id;
                    delim = true;
                }
                if(answerSetMap.containsKey(resultIdStringExpr)) {
                    String nameSet = answerSetMap.get(resultIdStringExpr) + "," + databaseExecutor.dbName;
                    answerSetMap.put(resultIdStringExpr, nameSet);
                }
                else answerSetMap.put(resultIdStringExpr, databaseExecutor.dbName);
            } catch (Exception e) {
                errorMessageMap.put(databaseExecutor.dbName, e.toString());
            }
        }
        for (Map.Entry<String, String> entry : answerSetMap.entrySet()) {
            logToFile(entry.getValue() + ":\n");
            logToFile("successfulexecution:" + entry.getKey() + "\n");
        }
        for (Map.Entry<String, String> entry: errorMessageMap.entrySet()) {
            logToFile(entry.getKey() + ":\n");
            String errorMessage = entry.getValue();
            if(errorMessage.charAt(errorMessage.length() - 1) != '\n')
                errorMessage += '\n';
            logToFile("errormessage:" + errorMessage);
        }
    }
}
