package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.ReportGeneration.ReportManager;
import XTest.TempTest.MultiTester;
import XTest.TestException.MismatchingResultException;
import XTest.XMLGeneration.ContextNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MainExecutor {

    Map<Integer, ContextNode> contextNodeMap = new HashMap<>();
    public List<DatabaseExecutor> databaseExecutorList = new ArrayList<>();
    Map<String, DatabaseExecutor> databaseExecutorNameMap = new HashMap<>();
    public String currentContext;
    ReportManager reportManager = null;

    public MainExecutor() {}
    public MainExecutor(ReportManager reportManager) {
        this.reportManager = reportManager;
    }

    public void setXPathGenerationContext(ContextNode root, String xmlDataContent) throws IOException, SQLException, XMLDBException, SaxonApiException {
        contextNodeMap = new HashMap<>();
        getContextNodeMap(root);
        currentContext = xmlDataContent;
        for(DatabaseExecutor databaseExecutor : databaseExecutorList) {
            databaseExecutor.setContextByContentWithCheck(xmlDataContent);
        }
    }

    void getContextNodeMap(ContextNode currentNode) {
        contextNodeMap.put(currentNode.id, currentNode);
        for(ContextNode childNode : currentNode.childList)
            getContextNodeMap(childNode);
    }

    public void registerDatabase(DatabaseExecutor databaseExecutor) {
        registerDatabase(databaseExecutor, null);
    }
    public void registerDatabase(DatabaseExecutor databaseExecutor, String databaseName) {
        if(databaseName != null)
            databaseExecutorNameMap.put(databaseName, databaseExecutor);
        databaseExecutorList.add(databaseExecutor);
    }

    public List<Integer> executeAndCompare(String XPath) throws SQLException, XMLDBException, IOException, SaxonApiException, MismatchingResultException {
        List<Integer> nodeIdResultSet = null;
        String lastDBName = null;
        System.out.println(XPath);
        for(DatabaseExecutor databaseExecutor : databaseExecutorList) {
            List<Integer> currentNodeIdResultSet = executeSingleProcessorGetIdList(XPath, databaseExecutor);
            if(nodeIdResultSet != null) {
                boolean checkResult = CommonUtils.compareList(nodeIdResultSet, currentNodeIdResultSet);
                if (checkResult == false) {
                    if(reportManager != null) {
                        System.out.println("Inconsistency found!");
                        reportManager.reportInconsistency(this, XPath);
                    }
                    else {
                        System.out.println(lastDBName);
                        System.out.println("+++++++++++++++++++++++++++++++");
                        System.out.println(nodeIdResultSet);
                        System.out.println(databaseExecutor.dbName);
                        System.out.println("-------------------------------");
                        System.out.println(currentNodeIdResultSet);
                    }
                    throw new MismatchingResultException();
                }
            }
            nodeIdResultSet = currentNodeIdResultSet;
            lastDBName = databaseExecutor.dbName;
        }
        return nodeIdResultSet;
    }

    public void cleanUp() throws SQLException, XMLDBException, IOException {
        for(DatabaseExecutor databaseExecutor : databaseExecutorList)
            databaseExecutor.clearContextWithCheck();
    }

    public void close() throws SQLException, XMLDBException, IOException {
        cleanUp();
        for(DatabaseExecutor databaseExecutor : databaseExecutorList)
            databaseExecutor.close();
    }

    public List<Integer> executeSingleProcessorGetIdList(String XPath) throws SQLException, XMLDBException, IOException, SaxonApiException {
        return executeSingleProcessorGetIdList(XPath, databaseExecutorList.get(0));
    }

    public List<Integer> executeSingleProcessorGetIdList(String XPath, String databaseName) throws SQLException, XMLDBException, IOException, SaxonApiException {
        return executeSingleProcessorGetIdList(XPath, databaseExecutorNameMap.get(databaseName));
    }

    public List<Integer> executeSingleProcessorGetIdList(String XPath, DatabaseExecutor databaseExecutor) throws SQLException, XMLDBException, IOException, SaxonApiException {
        return databaseExecutor.executeGetNodeIdList(XPath);
    }

    public String executeSingleProcessor(String XPath) throws SQLException, XMLDBException, IOException, SaxonApiException {
        return executeSingleProcessor(XPath, databaseExecutorList.get(0));
    }

    public String executeSingleProcessor(String XPath, String databaseName) throws SQLException, XMLDBException, IOException, SaxonApiException {
        DatabaseExecutor databaseExecutor = databaseExecutorNameMap.get(databaseName);
        return executeSingleProcessor(XPath, databaseExecutor);
    }

    public String executeSingleProcessor(String XPath, DatabaseExecutor databaseExecutor) throws SQLException, XMLDBException, IOException, SaxonApiException {
        System.out.println("Execute: " + XPath);
        String result = databaseExecutor.execute(XPath);
        return result;
    }

    public List<ContextNode> executeGetNodeList(String XPath) throws SQLException, XMLDBException, MismatchingResultException, IOException, SaxonApiException {
        List<Integer> nodeIdResultSet = executeAndCompare(XPath);
        return getNodeListFromIdList(nodeIdResultSet);
    }

    public List<ContextNode> getNodeListFromIdList(List<Integer> idList) {
        List<ContextNode> contextNodeList = new ArrayList<>();
        for(int id : idList)
            contextNodeList.add(contextNodeMap.get(id));
        return contextNodeList;
    }
}
