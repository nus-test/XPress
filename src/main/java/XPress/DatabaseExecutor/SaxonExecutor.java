package XPress.DatabaseExecutor;

import XPress.GlobalSettings;
import XPress.TestException.UnsupportedContextSetUpException;
import net.sf.saxon.s9api.*;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.SQLException;

@Executor
public class SaxonExecutor extends DatabaseExecutor {
    static SaxonExecutor saxonExecutor;
    Processor saxon;
    DocumentBuilder builder;
    XdmNode document;
    String mathNamespaceDeclarer = "declare namespace math=\"http://www.w3.org/2005/xpath-functions/math\";";
    String arrayNamespaceDeclarer = "declare namespace array=\"http://www.w3.org/2005/xpath-functions/array\";";

    private SaxonExecutor(String config) {
        dbName = "Saxon";
        saxon = new Processor(false);
        builder = saxon.newDocumentBuilder();
        dbXPathVersion = GlobalSettings.XPathVersion.VERSION_3;
    }

    public static void registerMap() {
        nameExecutorMap.put("Saxon", SaxonExecutor.class);
    }

    static public SaxonExecutor getInstance() {
        return getInstance("");
    }

    static public SaxonExecutor getInstance(String config) {
        if(saxonExecutor == null) {
            saxonExecutor = new SaxonExecutor(config);
        }
        return saxonExecutor;
    }

    @Override
    public void setContextByContentLow(String context) {
        try {
            Source src = new StreamSource(new StringReader(context));
            document = builder.build(src);
        } catch (Exception e) {
            System.out.println("Failed to set context for Saxon!");
            System.out.println(e);
        }
    }

    @Override
    void setContextWithCheck(String content, String fileAddr) throws SQLException, UnsupportedContextSetUpException, IOException {
        setContextByContentWithCheck(content);
    }

    @Override
    public void setContext(String info) throws SQLException, IOException, UnsupportedContextSetUpException {
        super.setContextByContent(info);
    }

    @Override
    public void setContextByFileLow(String fileAddr) throws UnsupportedContextSetUpException {
        throw new UnsupportedContextSetUpException();
    }

    @Override
    public String execute(String Xquery) throws SaxonApiException {
        XQueryCompiler compiler = saxon.newXQueryCompiler();
        XQueryExecutable exec = compiler.compile(arrayNamespaceDeclarer + mathNamespaceDeclarer + Xquery);
        XQueryEvaluator query = exec.load();
        query.setContextItem(document);
        XdmValue result = query.evaluate();
        String resultString = "";
        for(int i = 0; i < result.size(); i ++) {
            resultString += result.itemAt(i);
        }
        return resultString;
    }

    @Override
    public void close() {
    }
}
