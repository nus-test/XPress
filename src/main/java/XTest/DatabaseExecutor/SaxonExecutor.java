package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.TempTest.SaxonSimple;
import net.sf.saxon.s9api.*;
import org.xmldb.api.base.XMLDBException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;

public class SaxonExecutor implements DatabaseExecutor {
    static SaxonExecutor saxonExecutor;
    Processor saxon;
    DocumentBuilder builder;
    XdmNode document;


    private SaxonExecutor() {
        saxon = new Processor(false);
        builder = saxon.newDocumentBuilder();
    }

    SaxonExecutor getInstance() {
        if(saxonExecutor == null)
            saxonExecutor = new SaxonExecutor();
        return saxonExecutor;
    }

    @Override
    public void setContextByFile(String pathName) throws FileNotFoundException, SaxonApiException {
        URL url = SaxonSimple.class.getResource("xmldocs/" + pathName);
        File xmldocFile = new File(url.getFile());
        String xmlFileContent = CommonUtils.readInputStream(new FileInputStream(xmldocFile));
        setContextByContent(xmlFileContent);
    }

    @Override
    public void setContextByContent(String context) throws SaxonApiException {
        Source src = new StreamSource(new StringReader(context));
        document = builder.build(src);
    }

    @Override
    public void clearCurrentContext() throws XMLDBException, IOException {
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
