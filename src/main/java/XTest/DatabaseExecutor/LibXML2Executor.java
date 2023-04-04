package XTest.DatabaseExecutor;

import XTest.GlobalSettings;
import XTest.TestException.UnsupportedContextSetUpException;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibXML2Executor extends DatabaseExecutor {
    String fileAddr;
    static LibXML2Executor libXML2Executor;
    LibXML2ExecutorLow libXML2ExecutorLow;

    private LibXML2Executor() {
        libXML2ExecutorLow = new LibXML2ExecutorLow();
        dbName = "LibXML2";
        dbXPathVersion = GlobalSettings.XPathVersion.VERSION_1;
    }
    static public LibXML2Executor getInstance() throws SQLException, ClassNotFoundException {
        if(libXML2Executor == null)
            libXML2Executor = new LibXML2Executor();
        return libXML2Executor;
    }

    @Override
    void setContext(String info) throws SQLException, XMLDBException, IOException, SaxonApiException, UnsupportedContextSetUpException {
        setContextByFile(info);
    }

    @Override
    void setContextByFileLow(String fileAddr) throws IOException, XMLDBException, UnsupportedContextSetUpException {
        this.fileAddr = fileAddr;
    }

    @Override
    void setContextByContentLow(String content) throws SaxonApiException, SQLException, UnsupportedContextSetUpException {
        throw new UnsupportedContextSetUpException();
    }

    @Override
    public String execute(String Xquery) throws IOException, XMLDBException, SaxonApiException, SQLException {
        return libXML2ExecutorLow.execute(fileAddr, Xquery).trim();
    }

    @Override
    void setContextWithCheck(String content, String fileAddr) throws SQLException, UnsupportedContextSetUpException, XMLDBException, IOException, SaxonApiException {
        setContextByFileWithCheck(fileAddr);
    }

    @Override
    public void close() throws IOException, XMLDBException, SQLException {}

    @Override
    public List<Integer> executeGetNodeIdList(String Xquery) throws SQLException, XMLDBException, IOException, SaxonApiException {
        String result = execute(Xquery);
        if(result.length() == 0)
            return new ArrayList<>();
        String[] resultIdStrList = result.split("\\s+");
        List<Integer> resultIdList = new ArrayList<>();
        for(String id : resultIdStrList) {
            resultIdList.add(Integer.parseInt(id));
        }
        return resultIdList;
    }
}
