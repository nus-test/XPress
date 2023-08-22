package XPress.DatabaseExecutor;

import XPress.GlobalSettings;
import XPress.TestException.UnsupportedContextSetUpException;

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
        dbName = "libXML2";
        dbXPathVersion = GlobalSettings.XPathVersion.VERSION_1;
    }

    public static void registerMap() {
        nameExecutorMap.put("libXML2", LibXML2Executor.class);
    }

    static public LibXML2Executor getInstance() throws SQLException, ClassNotFoundException {
        if(libXML2Executor == null)
            libXML2Executor = new LibXML2Executor();
        return libXML2Executor;
    }

    @Override
    void setContext(String info) throws IOException, UnsupportedContextSetUpException {
        setContextByFile(info);
    }

    @Override
    void setContextByFileLow(String fileAddr) {
        this.fileAddr = fileAddr;
    }

    @Override
    void setContextByContentLow(String content) throws UnsupportedContextSetUpException {
        throw new UnsupportedContextSetUpException();
    }

    @Override
    public String execute(String Xquery) throws IOException, SQLException {
        return libXML2ExecutorLow.execute(fileAddr, Xquery).trim();
    }

    @Override
    void setContextWithCheck(String content, String fileAddr) throws SQLException, UnsupportedContextSetUpException, IOException {
        setContextByFileWithCheck(fileAddr);
    }

    @Override
    public void close() throws IOException, SQLException {}

    @Override
    public List<Integer> executeGetNodeIdList(String Xquery) throws SQLException, IOException {
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
