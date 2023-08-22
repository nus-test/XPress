package XPress.DatabaseExecutor;

import XPress.CommonUtils;
import XPress.GlobalSettings;
import XPress.ReportGeneration.KnownBugs;
import XPress.TestException.UnsupportedContextSetUpException;
import net.sf.saxon.s9api.SaxonApiException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.xmldb.api.base.XMLDBException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

public abstract class DatabaseExecutor {
    public String dbName;
    boolean clearFlag = false;
    public boolean compareFlag = true;
    String currentContext = null;

    public GlobalSettings.XPathVersion dbXPathVersion;
    static Map<String, Class> nameExecutorMap = new HashMap<>();

    /**
     * Register all database executor to name-database map.
     */
    public static void mapInit() {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(true);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Executor.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(
                "XPress.DatabaseExecutor")) {
            try {
                Class c = Class.forName(bd.getBeanClassName());
                Method factoryMethod = c.getDeclaredMethod("registerMap");
                factoryMethod.invoke(null, null);
            } catch (ClassNotFoundException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static DatabaseExecutor getExecutor(String dbName) {
        return getExecutor(dbName, "");
    }

    public static DatabaseExecutor getExecutor(String dbName, String config) {
        try {
            Class<?>[] argClasses = { String.class };
            Class dbClass = nameExecutorMap.get(dbName);
            if(dbClass == null) return null;
            Method factoryMethod = dbClass.getDeclaredMethod("getInstance", argClasses);
            return (DatabaseExecutor) factoryMethod.invoke(null, config);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Register current database executor to a main executor
     * @param mainExecutor
     */
    public void registerDatabase(MainExecutor mainExecutor) {
        mainExecutor.registerDatabase(this, dbName);
    }

    /**
     * Set context of current database with check to clear previous context (If context exist).
     * @param info Info needed for getting XML document; For database executors with default setContext() method reading from files
     *             Info is file address, while for those default is direct context reading Info is the document itself.
     * @throws SQLException
     * @throws IOException
     * @throws UnsupportedContextSetUpException
     */
    public void setContextWithCheck(String info) throws SQLException, IOException, UnsupportedContextSetUpException{
        clearContextWithCheck();
        setContext(info);
        clearFlag = true;
    }

    /**
     *  Set context of current database with check.
     * @param content XML document.
     * @param fileAddr File address XML document is stored in.
     * @throws SQLException
     * @throws UnsupportedContextSetUpException
     * @throws IOException
     */
    abstract void setContextWithCheck(String content, String fileAddr) throws SQLException, UnsupportedContextSetUpException, IOException;

    /**
     * Set context of current database through reading from file. With check to clear previous context (If context exist).
     * @param fileAddr File address pointing to the XML document file.
     * @throws SQLException
     * @throws IOException
     * @throws UnsupportedContextSetUpException
     */
    public void setContextByFileWithCheck(String fileAddr) throws SQLException, IOException, UnsupportedContextSetUpException {
        clearContextWithCheck();
        setContextByFile(fileAddr);
        clearFlag = true;
    }

    public void setContextByContentWithCheck(String content) throws SQLException, IOException, UnsupportedContextSetUpException {
        clearContextWithCheck();
        setContextByContent(content);
        clearFlag = true;
    }

    abstract void setContext(String info) throws SQLException, IOException, UnsupportedContextSetUpException;

    public void setContextByFile(String fileAddr) throws IOException, UnsupportedContextSetUpException {
        String xmlData = CommonUtils.readInputStream(new FileInputStream(fileAddr));
        this.currentContext = xmlData;
        setContextByFileLow(fileAddr);
    }

    abstract void setContextByFileLow(String fileAddr) throws IOException, UnsupportedContextSetUpException;

    void setContextByContent(String content) throws SQLException, UnsupportedContextSetUpException {
        this.currentContext = content;
        setContextByContentLow(content);
    }

    abstract void setContextByContentLow(String content) throws SQLException, UnsupportedContextSetUpException;

    public void clearContextWithCheck() throws SQLException, IOException {
        if (clearFlag) {
            clearCurrentContext();
            clearFlag = false;
        }
    }
    void clearCurrentContext() throws IOException, SQLException {};

    /**
     * Execute Xquery on database executor, return raw string result returned from database.
     * @param Xquery Xquery string
     * @return Raw string result returned from executing query string
     * @throws IOException
     * @throws SQLException
     * @throws SaxonApiException
     * @throws XMLDBException
     */
    public abstract String execute(String Xquery) throws IOException, SQLException, SaxonApiException, XMLDBException;

    /**
     * Execute Xquery on database executor, return node id list.
     * @param Xquery Xquery which returns sequence of nodes with unique ids.
     * @return List of selected nodes' id.
     * @throws SQLException
     * @throws IOException
     * @throws XMLDBException
     * @throws SaxonApiException
     */
    public List<Integer> executeGetNodeIdList(String Xquery) throws SQLException, IOException, XMLDBException, SaxonApiException {
        String result = execute(Xquery);
        return getNodeIdList(result, this.dbName);
    }
    public abstract void close() throws IOException, SQLException;

    /**
     * Return list of node ids from result string of nodes in XML format.
     * @param resultString Result string in XML format.
     * @return List of selected nodes' id.
     */
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

    /**
     * A wrapper to ignore known problems, get node id list from raw result string.
     * @param resultString Result string returned from database after query execution.
     * @param dbName Database where execution took place.
     * @return List of selected nodes' id after processing.
     */
    public static List<Integer> getNodeIdList(String resultString, String dbName) {
        List<Integer> resultList = getNodeIdList(resultString);
        if(dbName != null && dbName.startsWith("Exist") && KnownBugs.exist4811) {
            Set<Integer> s = new LinkedHashSet<>(resultList);
            resultList.clear();
            resultList.addAll(s);
        }
        return resultList;
    }
}
