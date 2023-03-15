package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.TempTest.SaxonSimple;
import net.sf.saxon.s9api.*;
import org.xmldb.api.base.XMLDBException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;

public class SaxonExecutor extends DatabaseExecutor {
    static SaxonExecutor saxonExecutor;
    Processor saxon;
    DocumentBuilder builder;
    XdmNode document;


    private SaxonExecutor() {
        dbName = "Saxon";
        saxon = new Processor(false);
        builder = saxon.newDocumentBuilder();
    }

    static public SaxonExecutor getInstance() {
        if(saxonExecutor == null)
            saxonExecutor = new SaxonExecutor();
        return saxonExecutor;
    }

    @Override
    public void setContextByContent(String context) throws SaxonApiException {
        Source src = new StreamSource(new StringReader(context));
        document = builder.build(src);
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
