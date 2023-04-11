package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.GlobalSettings;
import XTest.ReportGeneration.KnownBugs;
import XTest.TempTest.MySQLSimple;
import XTest.TestException.UnsupportedContextSetUpException;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public abstract class DatabaseExecutor {
    public String dbName;
    boolean clearFlag = false;
    String currentContext = null;

    GlobalSettings.XPathVersion dbXPathVersion;

    public void registerDatabase(MainExecutor mainExecutor) {
        mainExecutor.registerDatabase(this, dbName);
    }

    public void setContextWithCheck(String info) throws SQLException, XMLDBException, IOException, UnsupportedContextSetUpException, SaxonApiException {
        clearContextWithCheck();
        setContext(info);
        clearFlag = true;
    }

    abstract void setContextWithCheck(String content, String fileAddr) throws SQLException, UnsupportedContextSetUpException, XMLDBException, IOException, SaxonApiException;

    public void setContextByFileWithCheck(String fileAddr) throws SQLException, XMLDBException, IOException, SaxonApiException, UnsupportedContextSetUpException {
        clearContextWithCheck();
        setContextByFile(fileAddr);
        clearFlag = true;
    }

    public void setContextByContentWithCheck(String content) throws SQLException, XMLDBException, IOException, SaxonApiException, UnsupportedContextSetUpException {
        clearContextWithCheck();
        setContextByContent(content);
        clearFlag = true;
    }

    abstract void setContext(String info) throws SQLException, XMLDBException, IOException, SaxonApiException, UnsupportedContextSetUpException;

    public void setContextByFile(String fileAddr) throws SQLException, IOException, SaxonApiException, XMLDBException, UnsupportedContextSetUpException {
        String xmlData = CommonUtils.readInputStream(new FileInputStream(fileAddr));
        this.currentContext = xmlData;
        setContextByFileLow(fileAddr);
    }

    abstract void setContextByFileLow(String fileAddr) throws IOException, XMLDBException, UnsupportedContextSetUpException;

    void setContextByContent(String content) throws SaxonApiException, SQLException, XMLDBException, IOException, UnsupportedContextSetUpException {
        this.currentContext = content;
        setContextByContentLow(content);
    }

    abstract void setContextByContentLow(String content) throws SaxonApiException, SQLException, UnsupportedContextSetUpException;

    public void clearContextWithCheck() throws SQLException, XMLDBException, IOException {
        if (clearFlag) {
            clearCurrentContext();
            clearFlag = false;
        }
    }
    void clearCurrentContext() throws XMLDBException, IOException, SQLException {};
    public abstract String execute(String Xquery) throws IOException, XMLDBException, SaxonApiException, SQLException;
    public List<Integer> executeGetNodeIdList(String Xquery) throws SQLException, XMLDBException, IOException, SaxonApiException {
        String result = execute(Xquery);
        return getNodeIdList(result, this.dbName);
    }
    public abstract void close() throws IOException, XMLDBException, SQLException;

    public static List<Integer> getNodeIdList(String resultString) {
        List<Integer> nodeIdList = new ArrayList<>();
        Stack<Integer> tagStack = new Stack<>();
        for(int i = 0; i < resultString.length(); i ++) {
            if(i < resultString.length() - 1
                    && (resultString.substring(i, i + 2).equals("</") || resultString.substring(i, i + 2).equals("/>"))) {
                tagStack.pop();
            }
            else if(resultString.charAt(i) == '<')
                tagStack.add(0);
            if(tagStack.size() == 1 && i + 4 <= resultString.length() && resultString.substring(i, i + 4).equals("id=\"")) {
                nodeIdList.add(CommonUtils.getEnclosedInteger(resultString, i + 4));
            }
        }
        return nodeIdList;
    }

    public static List<Integer> getNodeIdList(String resultString, String dbName) {
        List<Integer> resultList = getNodeIdList(resultString);
        if(dbName != null && dbName.equals("Exist") && KnownBugs.exist4811) {
            Set<Integer> s = new LinkedHashSet<>(resultList);
            resultList.clear();
            resultList.addAll(s);
        }
        return resultList;
    }
}
