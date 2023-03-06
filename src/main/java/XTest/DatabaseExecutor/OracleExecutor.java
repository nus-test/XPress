package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.TempTest.MySQLSimple;
import org.xmldb.api.base.XMLDBException;

import java.io.ByteArrayInputStream;
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

    private OracleExecutor() throws ClassNotFoundException, SQLException {
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
    public void setContextByContent(String context) throws SQLException {
        statement.execute(createSQL);
        String insertSQL = "insert into test values (XMLType('" + context + "', nls_charset_id('AL32UTF8')))";
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
