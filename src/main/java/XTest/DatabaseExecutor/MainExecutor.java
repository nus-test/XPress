package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.ReportGeneration.ReportManager;
import XTest.TestException.MismatchingResultException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.TestException.UnsupportedContextSetUpException;
import XTest.XMLGeneration.AttributeNode;
import XTest.XMLGeneration.ContextNode;
import org.apache.commons.lang3.tuple.Pair;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import javax.naming.Context;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static java.lang.Math.max;

public class MainExecutor {

    String fileAddr = "C:\\app\\log\\autotest.xml";
    public Integer maxId = 0;
    public Map<Integer, ContextNode> contextNodeMap = new HashMap<>();
    public List<ContextNode> extraLeafNodeList = new ArrayList<>();
    public List<DatabaseExecutor> databaseExecutorList = new ArrayList<>();
    public Map<String, DatabaseExecutor> databaseExecutorNameMap = new HashMap<>();
    public String currentContext;
    ReportManager reportManager = null;
    boolean reportLock = false;

    public MainExecutor() {}
    public MainExecutor(ReportManager reportManager) {
        this.reportManager = reportManager;
    }

    public void setXPathGenerationContext(ContextNode root, String xmlDataContent) throws IOException, SQLException, XMLDBException, SaxonApiException, UnsupportedContextSetUpException {
        contextNodeMap = new HashMap<>();
        getContextNodeMap(root);
        setXPathGenerationContext(xmlDataContent);
    }

    public void setExtraLeafNodeContext(List<ContextNode> contextNodeList) {
        for(ContextNode contextNode : contextNodeList) {
            contextNodeMap.put(contextNode.id, contextNode);
            extraLeafNodeList.add(contextNode);
            maxId = max(contextNode.id, maxId);
        }
    }

    public void setXPathGenerationContext(String xmlDataContent) throws IOException, SQLException, XMLDBException, SaxonApiException, UnsupportedContextSetUpException {
        writeContextToFile(xmlDataContent);
        currentContext = xmlDataContent;
        for(DatabaseExecutor databaseExecutor : databaseExecutorList) {
            databaseExecutor.setContextWithCheck(xmlDataContent, fileAddr);
        }
    }

    public void writeContextToFile(String xmlDataContent) throws IOException {
        CommonUtils.writeContextToFile(xmlDataContent, fileAddr);
    }

    void getContextNodeMap(ContextNode currentNode) {
        contextNodeMap.put(currentNode.id, currentNode);
        maxId = max(maxId, currentNode.id);
        for(ContextNode childNode : currentNode.childList)
            getContextNodeMap(childNode);
    }

    public List<Pair<String, String>> getRandomTagNameTypePair(int length) {
        Set<String> nameSet = new HashSet<>();
        List<Pair<String, String>> randomList = new ArrayList<>();
        int cnt = 0, failures = 0;
        while(cnt < length) {
            int id = GlobalRandom.getInstance().nextInt(maxId - 1) + 1;
            ContextNode node = contextNodeMap.get(id);
            double prob = GlobalRandom.getInstance().nextDouble();
            if(prob < 0.6 && !nameSet.contains(node.tagName)) {
                nameSet.add(node.tagName);
                randomList.add(Pair.of(node.tagName, node.dataType.getValueHandler().officialTypeName));
                failures = 0;
                cnt ++;
            }
            else {
                AttributeNode attributeNode = GlobalRandom.getInstance().getRandomFromList(node.attributeList);
                if(!nameSet.contains(attributeNode.tagName)) {
                    nameSet.add(attributeNode.tagName);
                    randomList.add(Pair.of(attributeNode.tagName, attributeNode.dataType.getValueHandler().officialTypeName));
                    failures = 0;
                    cnt ++;
                }
                else failures += 1;
            }
            if(failures >= 3) break;
        }
        return randomList;
    }

    public void registerDatabase(DatabaseExecutor databaseExecutor) {
        registerDatabase(databaseExecutor, null);
    }
    public void registerDatabase(DatabaseExecutor databaseExecutor, String databaseName) {
        if(databaseName != null)
            databaseExecutorNameMap.put(databaseName, databaseExecutor);
        databaseExecutorList.add(databaseExecutor);
    }

    public List<Integer> executeAndCompare(String XPath) throws SQLException, XMLDBException, MismatchingResultException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        return executeAndCompare(XPath, false);
    }

    public List<Integer> executeAndCompare(String XPath, boolean ignoreException) throws SQLException, XMLDBException, IOException, SaxonApiException, MismatchingResultException, UnexpectedExceptionThrownException {
        List<Integer> nodeIdResultSet = null;
        String lastDBName = null;
        //System.out.println(XPath);
        for(DatabaseExecutor databaseExecutor : databaseExecutorList) {
            if(databaseExecutor.dbXPathVersion != GlobalSettings.xPathVersion)
                continue;
            if(!databaseExecutor.compareFlag)
                continue;
            List<Integer> currentNodeIdResultSet = null;
            try{
                currentNodeIdResultSet = executeSingleProcessorGetIdList(XPath, databaseExecutor);  
                currentNodeIdResultSet.sort(Integer::compareTo);
            }catch(Exception e) {
                if(ignoreException) return null;
                System.out.println("Unknown exception thrown!");
                throw new UnexpectedExceptionThrownException(e);
            }
            if(nodeIdResultSet != null) {
                boolean checkResult = CommonUtils.compareList(nodeIdResultSet, currentNodeIdResultSet);
                if (!checkResult) {
                    if(reportManager != null) {
                        System.out.println("Inconsistency found!");
                        reportManager.reportPotentialBug(this, XPath);
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

    public List<Integer> executeSingleProcessorGetIdList(String XPath) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        String dbName = GlobalSettings.defaultDBName;
        if(dbName == null)
            return executeSingleProcessorGetIdList(XPath, databaseExecutorList.get(0));
        else
            return executeSingleProcessorGetIdList(XPath, dbName);
    }

    public List<Integer> executeSingleProcessorGetIdList(String XPath, String databaseName) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        return executeSingleProcessorGetIdList(XPath, databaseExecutorNameMap.get(databaseName));
    }

    public List<ContextNode> executeSingleProcessorGetNodeList(String XPath) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        String dbName = GlobalSettings.defaultDBName;
        if(dbName == null)
            return executeSingleProcessorGetNodeList(XPath, databaseExecutorList.get(0));
        else
            return executeSingleProcessorGetNodeList(XPath, dbName);
    }

    public List<ContextNode> executeSingleProcessorGetNodeList(String XPath, String databaseName) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        return getNodeListFromIdList(executeSingleProcessorGetIdList(XPath, databaseExecutorNameMap.get(databaseName)));
    }

    public List<ContextNode> executeSingleProcessorGetNodeList(String XPath, DatabaseExecutor databaseExecutor) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        return getNodeListFromIdList(executeSingleProcessorGetIdList(XPath, databaseExecutor));
    }

    public List<Integer> executeSingleProcessorGetIdList(String XPath, DatabaseExecutor databaseExecutor) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        List<Integer> resultList = null;
        try {
            resultList = databaseExecutor.executeGetNodeIdList(XPath);
        } catch (Exception e) {
            if(reportManager != null) {
                if(databaseExecutor.dbName != null && !databaseExecutor.dbName.equals("Exist"))
                    reportManager.reportUnexpectedException(this, XPath, e);
            }
            else {
                System.out.println("---------------- Unexpected Exception Thrown ------------------");
                System.out.println(currentContext);
                System.out.println(XPath);
                System.out.println(databaseExecutor.dbName);
            }
            throw new UnexpectedExceptionThrownException(e);
        }
        return resultList;
    }

    public String executeSingleProcessor(String XPath) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        return executeSingleProcessor(XPath, databaseExecutorList.get(0));
    }

    public String executeSingleProcessor(String XPath, String databaseName) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        DatabaseExecutor databaseExecutor = databaseExecutorNameMap.get(databaseName);
        return executeSingleProcessor(XPath, databaseExecutor);
    }

    public void setReportLock() {
       reportLock = true;
    }

    public void unlockReportLock() {
        reportLock = false;
    }

    public String executeSingleProcessor(String XPath, DatabaseExecutor databaseExecutor) throws SQLException, XMLDBException, IOException, SaxonApiException, UnexpectedExceptionThrownException {
        //System.out.println("Execute: " + XPath);
        String result = null;
        try {
            result = databaseExecutor.execute(XPath);
        } catch(Exception e) {
            if(reportManager != null && !reportLock) {
                if(databaseExecutor.dbName != null && !databaseExecutor.dbName.equals("Exist"))
                    reportManager.reportUnexpectedException(this, XPath, e);
            }
            else {
                System.out.println("---------------- Unexpected Exception Thrown ------------------");
                System.out.println(currentContext);
                System.out.println(XPath);
                System.out.println(databaseExecutor.dbName);
            }
            throw new UnexpectedExceptionThrownException(e);
        }
        return result;
    }

    public List<ContextNode> getNodeListFromIdList(List<Integer> idList) {
        List<ContextNode> contextNodeList = new ArrayList<>();
        for(int id : idList)
            contextNodeList.add(contextNodeMap.get(id));
        return contextNodeList;
    }
}
