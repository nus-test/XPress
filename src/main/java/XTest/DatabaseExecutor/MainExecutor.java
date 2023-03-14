package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.StringUtils;
import XTest.TestException.MismatchingResultException;
import XTest.XMLGeneration.ContextNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MainExecutor {

    Map<Integer, ContextNode> contextNodeMap = new HashMap<>();
    List<DatabaseExecutor> databaseExecutorList = new ArrayList<>();
    Map<String, DatabaseExecutor> databaseExecutorNameMap = new HashMap<>();

    public MainExecutor() {

    }

    public void setXPathGenerationContext(ContextNode root, String xmlDataContent) throws IOException, SQLException, XMLDBException, SaxonApiException {
        contextNodeMap = new HashMap<>();
        getContextNodeMap(root);
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
        databaseExecutorList.add(databaseExecutor);
    }
    public void registerDatabase(DatabaseExecutor databaseExecutor, String databaseName) {
        databaseExecutorNameMap.put(databaseName, databaseExecutor);
        databaseExecutorList.add(databaseExecutor);
    }

    public List<Integer> execute(String XPath) throws SQLException, XMLDBException, IOException, SaxonApiException, MismatchingResultException {
        List<Integer> nodeIdResultSet = null;
        for(DatabaseExecutor databaseExecutor : databaseExecutorList) {
            String result = executeSingleProcessor(XPath, databaseExecutor);
            List<Integer> currentNodeIdResultSet = getNodeIdList(result);
            if(nodeIdResultSet != null) {
                boolean checkResult = CommonUtils.compareList(nodeIdResultSet, currentNodeIdResultSet);
                if (checkResult == false) {
                    throw new MismatchingResultException();
                }
            }
            nodeIdResultSet = currentNodeIdResultSet;
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
        List<Integer> nodeIdResultSet = execute(XPath);
        return getNodeListFromIdList(nodeIdResultSet);
    }

    public List<Integer> getNodeIdList(String resultString) {
        List<Integer> nodeIdList = new ArrayList<>();
        Stack<Integer> tagStack = new Stack<>();
        for(int i = 0; i < resultString.length(); i ++) {
            if(i < resultString.length() - 1
                    && resultString.substring(i, i + 2).equals("</")) {
                tagStack.pop();
            }
            else if(resultString.charAt(i) == '<')
                tagStack.add(0);
            if(tagStack.size() == 1 && i + 4 <= resultString.length() && resultString.substring(i, i + 4).equals("id=\"")) {
                nodeIdList.add(CommonUtils.getEnclosedInteger(resultString, i + 4));
            }
        }
        nodeIdList.sort(Integer::compareTo);
        return nodeIdList;
    }

    public List<ContextNode> getNodeListFromIdList(List<Integer> idList) {
        List<ContextNode> contextNodeList = new ArrayList<>();
        for(int id : idList)
            contextNodeList.add(contextNodeMap.get(id));
        return contextNodeList;
    }
}
