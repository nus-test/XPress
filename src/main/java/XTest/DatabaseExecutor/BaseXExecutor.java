package XTest.DatabaseExecutor;

import XTest.TempTest.BaseXClient;
import XTest.TempTest.BaseXSimple;
import org.apache.commons.lang3.NotImplementedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BaseXExecutor implements DatabaseExecutor {

    static BaseXExecutor baseXExecutor;
    BaseXClient BaseXSession;
    String username = "admin";
    String password = "shuxin";

    private BaseXExecutor() throws IOException {
        BaseXSession = new BaseXClient("localhost", 1984, username, password);
    }

    BaseXExecutor getInstance() throws IOException {
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
    public void setContextByContent(String context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearCurrentContext() throws IOException {
        BaseXSession.execute("drop db test");
    }

    @Override
    public String execute(String Xquery) throws IOException {
        return BaseXSession.execute("xquery " + Xquery);
    }

    @Override
    public void close() throws IOException {
        BaseXSession.execute("drop db test");
    }
}
