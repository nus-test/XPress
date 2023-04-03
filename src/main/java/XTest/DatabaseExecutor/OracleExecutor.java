package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.TempTest.MySQLSimple;
import XTest.TestException.UnsupportedContextSetUpException;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private OracleExecutor() throws ClassNotFoundException, SQLException {
        dbName = "Oracle";
        Class.forName("oracle.jdbc.OracleDriver");
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
    }

    static public OracleExecutor getInstance() throws SQLException, ClassNotFoundException {
        if(oracleExecutor == null)
            oracleExecutor = new OracleExecutor();
        return oracleExecutor;
    }

    @Override
    void setContextWithCheck(String content, String fileAddr) throws SQLException, UnsupportedContextSetUpException, XMLDBException, IOException, SaxonApiException {
        setContextByContentWithCheck(content);
    }

    @Override
    public void setContext(String info) throws SQLException, XMLDBException, IOException, SaxonApiException, UnsupportedContextSetUpException {
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
    public void clearCurrentContext() throws XMLDBException, IOException, SQLException {
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
            System.out.println("");
        }
        resultSet.close();
        return result;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
