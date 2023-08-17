package XPress.DatabaseExecutor;

import XPress.GlobalSettings;
import XPress.TestException.UnsupportedContextSetUpException;

import java.io.IOException;
import java.sql.*;

public class OracleExecutor extends DatabaseExecutor {
    static OracleExecutor oracleExecutor;
    String url = "jdbc:oracle:thin:@localhost:1521/XE";
    String username = "test";
    String password = "shuxin";
    Connection connection;
    Statement statement;
    String createSQL = "create table test of xmltype";
    String dropSQL = "drop table test";

    private OracleExecutor() throws SQLException {
        dbName = "Oracle";
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
        dbXPathVersion = GlobalSettings.XPathVersion.VERSION_1;
    }

    static public OracleExecutor getInstance() throws SQLException, ClassNotFoundException {
        if(oracleExecutor == null)
            oracleExecutor = new OracleExecutor();
        return oracleExecutor;
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
    public void setContextByContentLow(String content) throws SQLException {
        statement.execute(createSQL);
        String insertSQL = "insert into test values (XMLType('" + content + "', nls_charset_id('AL32UTF8')))";
        statement.execute(insertSQL);
    }

    @Override
    public void clearCurrentContext() throws SQLException {
        System.out.println("Dropped Oracle!");
        statement.execute(dropSQL);
    }

    @Override
    public String execute(String Xquery) throws SQLException {
        String selectSQL = "select extract(OBJECT_VALUE, '" + Xquery + "') from test";
        ResultSet resultSet = statement.executeQuery(selectSQL);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        String result = "";
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                result += resultSet.getString(i);
            }
        }
        resultSet.close();
        return result;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
