package XPress.DatabaseExecutor;

import XPress.CommonUtils;
import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.GlobalRandom;
import XPress.GlobalSettings;
import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.DatatypeControl.ValueHandler.XMLStringHandler;
import XPress.ReportGeneration.ReportManager;
import XPress.TestException.MismatchingResultException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.TestException.UnsupportedContextSetUpException;
import XPress.XMLGeneration.AttributeNode;
import XPress.XMLGeneration.ContextNode;
import XPress.XMLGeneration.XMLContext;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static java.lang.Math.max;

public class MainExecutor {

    String fileAddr = "C:\\app\\log\\autotest.xml";
    public Integer maxId = 0;
    public Integer maxIdContainsLeaf = 0;
    public Map<Integer, ContextNode> contextNodeMap = new HashMap<>();
    public List<String> namespaceList = new ArrayList<>();
    public List<Pair<String, String>> prefixNamespaceList = new ArrayList<>();
    public Map<String, List<String>> namespacePrefixMap = new HashMap<>();
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

    public void setXPathGenerationContext(XMLContext context) throws IOException, SQLException, UnsupportedContextSetUpException {
        contextNodeMap = new HashMap<>();
        namespacePrefixMap = new HashMap<>();
        prefixNamespaceList = new ArrayList<>();
        getContextNodeMap(context.getRoot());
        setXPathGenerationContext(context.getXmlContent());
        namespaceList = context.getNamespaceList();
        getNamespace(context.getRoot());
        getPrefixNamespaceList();
    }

    public void getPrefixNamespaceList() {
        Set<String> prefixes = new HashSet<>();
        for(String namespace : namespaceList) {
            addUniquePrefixForNamespace(namespace, prefixes);
        }
        int additional = GlobalRandom.getInstance().nextInt(3) + 1;
        for(int i = 0; i < additional; i ++) {
            String namespace = GlobalRandom.getInstance().getRandomFromList(namespaceList);
            addUniquePrefixForNamespace(namespace, prefixes);
        }
    }

    public void addUniquePrefixForNamespace(String namespace, Set<String> prefixes) {
        String prefix = getUniquePrefix(prefixes);
        prefixNamespaceList.add(Pair.of(prefix, namespace));
        if(!namespacePrefixMap.containsKey(namespace))
            namespacePrefixMap.put(namespace, new ArrayList<>());
        namespacePrefixMap.get(namespace).add(prefix);
    }

    public String getUniquePrefix(Set<String> prefixes) {
        String prefix;
        while (true) {
            prefix = ((XMLStringHandler) XMLString.getInstance().getValueHandler()).getRandomValueEng(GlobalRandom.getInstance().nextInt(5) + 1);
            if(!prefixes.contains(prefix)) {
                prefixes.add(prefix);
                return prefix;
            }
        }
    }

    public void setExtraLeafNodeContext(List<ContextNode> contextNodeList) {
        for(ContextNode contextNode : contextNodeList) {
            contextNodeMap.put(contextNode.id, contextNode);
            extraLeafNodeList.add(contextNode);
            maxIdContainsLeaf = max(contextNode.id, maxIdContainsLeaf);
        }
    }

    public void setXPathGenerationContext(String xmlDataContent) throws IOException, SQLException, UnsupportedContextSetUpException {
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
        maxIdContainsLeaf = max(maxIdContainsLeaf, maxId);
        for(ContextNode childNode : currentNode.childList)
            getContextNodeMap(childNode);
    }

    void getNamespace(ContextNode currentNode) {
        try {
            currentNode.namespace = executeSingleProcessor("//*[@id=\"" + currentNode.id + "\"]/namespace-uri-from-QName(node-name())");
        } catch(Exception e) {
            System.out.println(currentNode.id + " " + e);
            String s = "//*[@id=\"" + currentNode.id + "\"]/namespace-uri-from-QName(node-name())";
            System.out.println(s);
            throw new RuntimeException("Failed to get namespace of node.");
        }
        for(ContextNode childNode : currentNode.childList)
            getNamespace(childNode);
    }

    public List<Pair<String, String>> getRandomTagNameTypePair(int length) {
        Set<String> nameSet = new HashSet<>();
        List<Pair<String, String>> randomList = new ArrayList<>();
        int cnt = 0, failures = 0;
        while(cnt < length) {
            int id = GlobalRandom.getInstance().nextInt(maxId) + 1;
            ContextNode node = contextNodeMap.get(id);
            AttributeNode attributeNode = GlobalRandom.getInstance().getRandomFromList(node.attributeList);
            if(!nameSet.contains(attributeNode.tagName)) {
                nameSet.add(attributeNode.tagName);
                randomList.add(Pair.of(attributeNode.tagName, attributeNode.dataType.officialTypeName));
                failures = 0;
                cnt ++;
            }
            else failures += 1;
            if(failures >= 10) break;
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

    public List<Integer> executeAndCompare(String XPath) throws MismatchingResultException, UnexpectedExceptionThrownException, IOException {
        return executeAndCompare(XPath, false);
    }

    public List<Integer> executeAndCompare(String XPath, boolean ignoreException) throws MismatchingResultException, UnexpectedExceptionThrownException, IOException {
        return executeAndCompare(XPath, false, false);
    }

    public List<Integer> executeAndCompare(Pair<List<Pair<Integer,Integer>>, String> XPathRecord) throws SQLException, MismatchingResultException, UnexpectedExceptionThrownException, IOException {
        return executeAndCompare(XPathRecord, false);
    }


    public List<Integer> executeAndCompare(Pair<List<Pair<Integer,Integer>>, String> XPathRecord, boolean ignoreException) throws SQLException, MismatchingResultException, UnexpectedExceptionThrownException, IOException {
        String XPath = XPathRecord.getRight();
        List<Integer> resultList = executeAndCompareResultOnly(XPath, ignoreException);
        if(resultList != null) return resultList;
        for(int k = 0; k < XPathRecord.getLeft().size(); k ++) {
            String subPath = XPath.substring(XPathRecord.getLeft().get(k).getLeft(),
                    XPathRecord.getLeft().get(k).getRight());
            if(subPath.startsWith("(")) {
                subPath = "//*/" + subPath;
            } else subPath = "//" + subPath;
            try {
                executeAndCompare(subPath, ignoreException);
            } catch (Exception e) {
                return null;
            }
        }
        return executeAndCompare(XPath, ignoreException);
    }

    public List<Integer> executeAndCompareResultOnly(String XPath, boolean ignoreException) throws IOException, MismatchingResultException, UnexpectedExceptionThrownException {
        return executeAndCompare(XPath, ignoreException, true);
    }

    public List<Integer> executeAndCompare(String XPath, boolean ignoreException, boolean resultOnly) throws IOException, MismatchingResultException, UnexpectedExceptionThrownException {
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
                if(reportManager != null) {
                    System.out.println("Inconsistency found!");
                    // TODO: Test eXist index
                    reportManager.reportPotentialBug(this, XPath);
                }
                //System.out.println("Unknown exception thrown!");
                throw new UnexpectedExceptionThrownException(e, databaseExecutor.dbName);
            }
            if(nodeIdResultSet != null) {
                boolean checkResult = CommonUtils.compareList(nodeIdResultSet, currentNodeIdResultSet);
                if (!checkResult) {
                    if(resultOnly) return null;
                    if(reportManager != null) {
                        //System.out.println("Inconsistency found!");
                        reportManager.reportPotentialBug(this, XPath);
                    }
//                    else {
//                        System.out.println(lastDBName);
//                        System.out.println("+++++++++++++++++++++++++++++++");
//                        System.out.println(nodeIdResultSet);
//                        System.out.println(databaseExecutor.dbName);
//                        System.out.println("-------------------------------");
//                        System.out.println(currentNodeIdResultSet);
//                    }
                    throw new MismatchingResultException();
                }
            }
            nodeIdResultSet = currentNodeIdResultSet;
            lastDBName = databaseExecutor.dbName;
        }
        return nodeIdResultSet;
    }

    public void cleanUp() throws SQLException, IOException {
        for(DatabaseExecutor databaseExecutor : databaseExecutorList)
            databaseExecutor.clearContextWithCheck();
    }

    public void close() throws SQLException, IOException {
        try {
            if(reportManager != null) reportManager.close();
            cleanUp();
            for (DatabaseExecutor databaseExecutor : databaseExecutorList)
                databaseExecutor.close();
        } catch(Exception e) {
            System.out.println("Failed to clean up executors!");
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    public List<Integer> executeSingleProcessorGetIdList(String XPath) throws UnexpectedExceptionThrownException {
        String dbName = GlobalSettings.defaultDBName;
        if(dbName == null)
            return executeSingleProcessorGetIdList(XPath, databaseExecutorList.get(0));
        else
            return executeSingleProcessorGetIdList(XPath, dbName);
    }

    public List<Integer> executeSingleProcessorGetIdList(String XPath, String databaseName) throws UnexpectedExceptionThrownException {
        return executeSingleProcessorGetIdList(XPath, databaseExecutorNameMap.get(databaseName));
    }

    public List<ContextNode> executeSingleProcessorGetNodeList(String XPath) throws UnexpectedExceptionThrownException {
        String dbName = GlobalSettings.defaultDBName;
        if(dbName == null)
            return executeSingleProcessorGetNodeList(XPath, databaseExecutorList.get(0));
        else
            return executeSingleProcessorGetNodeList(XPath, dbName);
    }

    public List<ContextNode> executeSingleProcessorGetNodeList(String XPath, String databaseName) throws UnexpectedExceptionThrownException {
        return getNodeListFromIdList(executeSingleProcessorGetIdList(XPath, databaseExecutorNameMap.get(databaseName)));
    }

    public List<ContextNode> executeSingleProcessorGetNodeList(String XPath, DatabaseExecutor databaseExecutor) throws UnexpectedExceptionThrownException {
        return getNodeListFromIdList(executeSingleProcessorGetIdList(XPath, databaseExecutor));
    }

    public String wrapWithNamespaceDeclaration(String XPath) {
        if(GlobalSettings.useNamespace) {
            for (Pair prefixNamespacePair : prefixNamespaceList) {
                XPath = "declare namespace " +
                        prefixNamespacePair.getLeft() +
                        " =\"" + prefixNamespacePair.getRight() + "\";" + XPath;
            }
        }
        return XPath;
    }

    public List<Integer> executeSingleProcessorGetIdList(String XPath, DatabaseExecutor databaseExecutor) throws UnexpectedExceptionThrownException {
        List<Integer> resultList = null;
        try {
            resultList = databaseExecutor.executeGetNodeIdList(wrapWithNamespaceDeclaration(XPath));
        } catch (Exception e) {
            if(reportManager != null) {
                // TODO: Currently disabled for exist testing
                //                if(databaseExecutor.dbName != null && !databaseExecutor.dbName.equals("Exist"))
//                    reportManager.reportUnexpectedException(this, XPath, e);
            }
            else {
//                System.out.println("---------------- Unexpected Exception Thrown ------------------");
//                System.out.println(currentContext);
//                System.out.println(XPath);
//                System.out.println(databaseExecutor.dbName);
            }
            throw new UnexpectedExceptionThrownException(e, databaseExecutor.dbName);
        }
        return resultList;
    }

    public String executeSingleProcessor(String XPath) throws UnexpectedExceptionThrownException {
        return executeSingleProcessor(XPath, databaseExecutorList.get(0));
    }

    public String executeSingleProcessor(String XPath, String databaseName) throws UnexpectedExceptionThrownException {
        DatabaseExecutor databaseExecutor = databaseExecutorNameMap.get(databaseName);
        return executeSingleProcessor(XPath, databaseExecutor);
    }

    public void setReportLock() {
        reportLock = true;
    }

    public void unlockReportLock() {
        reportLock = false;
    }

    public String executeSingleProcessor(String XPath, DatabaseExecutor databaseExecutor) throws UnexpectedExceptionThrownException {
        //System.out.println("Execute: " + XPath);
        String result = null;
        try {
            result = databaseExecutor.execute(wrapWithNamespaceDeclaration(XPath));
        } catch(Exception e) {
            if(reportManager != null) {
                // TODO: Currently diabled for exist testing
                //                if(databaseExecutor.dbName != null && !databaseExecutor.dbName.equals("Exist"))
//                    reportManager.reportUnexpectedException(this, XPath, e);
            }
            else {
                System.out.println("---------------- Unexpected Exception Thrown ------------------");
                System.out.println(currentContext);
                System.out.println(XPath);
                System.out.println(databaseExecutor.dbName);
            }
            throw new UnexpectedExceptionThrownException(e, databaseExecutor.dbName);
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
