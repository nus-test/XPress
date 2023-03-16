package XTest.DatabaseExecutor;

import XTest.TempTest.BaseXClient;
import XTest.TempTest.BaseXSimple;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BaseXExecutor extends DatabaseExecutor {

    static BaseXExecutor baseXExecutor;
    BaseXClient BaseXSession;
    String username = "admin";
    String password = "shuxin";

    private BaseXExecutor() throws IOException {
        dbName = "BaseX";
        BaseXSession = new BaseXClient("localhost", 1984, username, password);
    }

    static public BaseXExecutor getInstance() throws IOException {
        if(baseXExecutor == null) {
            baseXExecutor = new BaseXExecutor();
        }
        return baseXExecutor;
    }

    @Override
    public void setContextByFile(String pathName) throws IOException {
        InputStream xmlData =
                new ByteArrayInputStream(BaseXSimple.class.getResourceAsStream("/xmldocs/" + pathName).readAllBytes());
        BaseXSession.execute("create db test");
        BaseXSession.add(pathName, xmlData);
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
