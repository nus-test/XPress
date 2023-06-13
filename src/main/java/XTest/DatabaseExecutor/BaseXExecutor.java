package XTest.DatabaseExecutor;

import XTest.GlobalSettings;
import XTest.TempTest.BaseXClient;
import XTest.TestException.UnsupportedContextSetUpException;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class BaseXExecutor extends DatabaseExecutor {

    static BaseXExecutor baseXExecutor;
    BaseXClient BaseXSession;
    String username = "admin";
    String password = "shuxin";

    private BaseXExecutor() {
        try {
            dbName = "BaseX";
            dbXPathVersion = GlobalSettings.XPathVersion.VERSION_3;
            BaseXSession = new BaseXClient("localhost", 1984, username, password);
        } catch(Exception e) {
            System.out.println("Failed to instantiate BaseX executor!");
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    static public BaseXExecutor getInstance() {
        if(baseXExecutor == null) {
            baseXExecutor = new BaseXExecutor();
        }
        return baseXExecutor;
    }

    @Override
    void setContextWithCheck(String content, String fileAddr) throws SQLException, UnsupportedContextSetUpException, IOException {
        setContextByFileWithCheck(fileAddr);
    }

    @Override
    public void setContext(String info) throws SQLException, IOException, UnsupportedContextSetUpException {
        super.setContextByFile(info);
    }

    @Override
    public void setContextByFileLow(String fileAddr) throws IOException {
        InputStream xmlData = new FileInputStream(fileAddr);
        BaseXSession.execute("create db test");
        BaseXSession.add("autotest", xmlData);
    }

    @Override
    public void setContextByContentLow(String content) throws UnsupportedContextSetUpException {
        throw new UnsupportedContextSetUpException();
    }


    @Override
    public void clearCurrentContext() throws IOException {
        System.out.println("Cleared baseX context!");
        BaseXSession.execute("drop db test");
    }

    @Override
    public String execute(String Xquery) throws IOException {
        return BaseXSession.execute("xquery " + Xquery);
    }



    @Override
    public void close() throws IOException {
    }
}
