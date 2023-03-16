package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.TempTest.MySQLSimple;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class DatabaseExecutor {
    public String dbName;
    boolean clearFlag = false;
    String currentContext = null;

    public void registerDatabase(MainExecutor mainExecutor) {
        mainExecutor.registerDatabase(this, dbName);
    }

    public void setContextByFileWithCheck(String pathName) throws SQLException, XMLDBException, IOException, SaxonApiException {
        clearContextWithCheck();
        String xmlData =
                CommonUtils.readInputStream(
                        new ByteArrayInputStream(MySQLSimple.class.getResourceAsStream("/xmldocs/" + pathName).readAllBytes()));
        this.currentContext = xmlData;
        setContextByFile(pathName);
        clearFlag = true;
    }

    public void setContextByContentWithCheck(String context) throws SQLException, XMLDBException, IOException, SaxonApiException {
        clearContextWithCheck();
        this.currentContext = context;
        setContextByContent(context);
        clearFlag = true;
    }

    public void setContextByFile(String pathName) throws SQLException, IOException, SaxonApiException, XMLDBException {
        String xmlData =
                CommonUtils.readInputStream(
                        new ByteArrayInputStream(MySQLSimple.class.getResourceAsStream("/xmldocs/" + pathName).readAllBytes()));
        setContextByContentWithCheck(xmlData);
    }

    void setContextByContent(String context) throws SaxonApiException, SQLException, XMLDBException, IOException {
        FileWriter writer =
                new FileWriter((this.getClass().getResource("/xmldocs/autotest.xml").getPath()));
        writer.write(context);
        System.out.println(context);
        writer.close();
        setContextByFileWithCheck("autotest.xml");
    }

    public void clearContextWithCheck() throws SQLException, XMLDBException, IOException {
        if(clearFlag)
            clearCurrentContext();
    }
    void clearCurrentContext() throws XMLDBException, IOException, SQLException {};
    public abstract String execute(String Xquery) throws IOException, XMLDBException, SaxonApiException, SQLException;
    public List<Integer> executeGetNodeIdList(String Xquery) throws SQLException, XMLDBException, IOException, SaxonApiException {
        String result = execute(Xquery);
        return getNodeIdList(result);
    }
    public abstract void close() throws IOException, XMLDBException, SQLException;

    public static List<Integer> getNodeIdList(String resultString) {
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
}
