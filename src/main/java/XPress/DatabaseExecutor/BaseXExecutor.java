package XPress.DatabaseExecutor;

import XPress.GlobalSettings;
import XPress.TempTest.BaseXClient;
import XPress.TestException.UnsupportedContextSetUpException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@Executor
public class BaseXExecutor extends DatabaseExecutor {

    static BaseXExecutor baseXExecutor;
    BaseXClient BaseXSession;
    String username = "admin";
    String password = "shuxin";

    private BaseXExecutor(String config) {
        try {
            dbName = "BaseX";
            dbXPathVersion = GlobalSettings.XPathVersion.VERSION_3;
            if(config.length() > 0) {
                String[] configs = config.split("[\\s,]+");
                username = configs[0];
                password = configs[1];
            }
            BaseXSession = new BaseXClient("localhost", 1984, username, password);
        } catch(Exception e) {
            System.out.println("Failed to instantiate BaseX executor!");
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    public static void registerMap() {
        nameExecutorMap.put("BaseX", BaseXExecutor.class);
    }

    static public BaseXExecutor getInstance() {
        return getInstance("");
    }

    static public BaseXExecutor getInstance(String config) {
        if(baseXExecutor == null) {
            baseXExecutor = new BaseXExecutor(config);
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
        BaseXSession.execute("drop db test");
    }

    @Override
    public String execute(String Xquery) throws IOException {
        return BaseXSession.execute("xquery " + Xquery);
    }

    public void createIndex() throws IOException {
        BaseXSession.execute("create index attribute");
    }



    @Override
    public void close() throws IOException {
    }
}
