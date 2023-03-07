package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.StringUtils;
import XTest.TestException.MismatchingResultException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PrefixQualifier;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainExecutor {

    Map<Integer, ContextNode> contextNodeMap = new HashMap<>();
    List<DatabaseExecutor> databaseExecutorList = new ArrayList<>();

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

    public List<Integer> execute(String XPath) throws SQLException, XMLDBException, IOException, SaxonApiException, MismatchingResultException {
        List<Integer> nodeIdResultSet = null;
        for(DatabaseExecutor databaseExecutor : databaseExecutorList) {
            String result = databaseExecutor.execute(XPath);
            List<Integer> currentNodeIdResultSet = getNodeIdList(result);
            boolean checkResult = CommonUtils.compareList(nodeIdResultSet, currentNodeIdResultSet);
            if(checkResult == false) {
                throw new MismatchingResultException();
            }
            nodeIdResultSet = currentNodeIdResultSet;
        }
        return nodeIdResultSet;
    }

    public List<ContextNode> executeGetNodeList(String XPath) throws SQLException, XMLDBException, MismatchingResultException, IOException, SaxonApiException {
        List<Integer> nodeIdResultSet = execute(XPath);
        return getNodeListFromIdList(nodeIdResultSet);
    }

    public List<Integer> getNodeIdList(String resultString) {
        List<Integer> occurrenceList = StringUtils.getOccurrenceInString(resultString, "<id=\"");
        List<Integer> nodeIdList = new ArrayList<>();
        for(Integer index: occurrenceList) {
            int endIndex = resultString.indexOf("\"", index + 5);
            int nodeId = Integer.parseInt(resultString.substring(index + 5, endIndex));
            nodeIdList.add(nodeId);
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
