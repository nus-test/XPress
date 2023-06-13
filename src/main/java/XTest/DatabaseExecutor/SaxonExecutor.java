package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.GlobalSettings;
import XTest.TempTest.SaxonSimple;
import XTest.TestException.UnsupportedContextSetUpException;
import net.sf.saxon.s9api.*;
import org.xmldb.api.base.XMLDBException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;

public class SaxonExecutor extends DatabaseExecutor {
    static SaxonExecutor saxonExecutor;
    Processor saxon;
    DocumentBuilder builder;
    XdmNode document;


    private SaxonExecutor() {
        dbName = "Saxon";
        saxon = new Processor(false);
        builder = saxon.newDocumentBuilder();
        dbXPathVersion = GlobalSettings.XPathVersion.VERSION_3;
    }

    static public SaxonExecutor getInstance() {
        if(saxonExecutor == null)
            saxonExecutor = new SaxonExecutor();
        return saxonExecutor;
    }

    @Override
    public void setContextByContentLow(String context) {
        try {
            Source src = new StreamSource(new StringReader(context));
            document = builder.build(src);
        } catch (Exception e) {
            System.out.println("Failed to set context for eXist!");
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
        XQueryExecutable exec = compiler.compile(Xquery);
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
