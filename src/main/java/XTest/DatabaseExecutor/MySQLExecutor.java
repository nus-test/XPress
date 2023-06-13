package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.GlobalSettings;
import XTest.TempTest.MySQLSimple;
import XTest.TestException.UnsupportedContextSetUpException;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class MySQLExecutor extends DatabaseExecutor {
    static MySQLExecutor mySQLExecutor;
    Connection connection;
    String xmlDataContent;

    private MySQLExecutor() throws SQLException {
        dbName = "MySQL";
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test?useTimezone=true&serverTimezone=UTC",
                "root", "shuxin");
        dbXPathVersion = GlobalSettings.XPathVersion.VERSION_1;
    }

    static public MySQLExecutor getInstance() throws SQLException, ClassNotFoundException {
        if(mySQLExecutor == null)
            mySQLExecutor = new MySQLExecutor();
        return mySQLExecutor;
    }

    @Override
    public void setContextByFileLow(String pathName) throws IOException {
        xmlDataContent = CommonUtils.readInputStream(
                new ByteArrayInputStream(MySQLSimple.class.getResourceAsStream("xmldocs/" + pathName).readAllBytes()));
    }

    @Override
    public void setContextByContentLow(String context) {
        xmlDataContent = context;
    }

    @Override
    public void setContext(String info) throws SQLException, IOException, UnsupportedContextSetUpException {
        super.setContextByContent(info);
    }

    @Override
    void setContextWithCheck(String content, String fileAddr) throws SQLException, UnsupportedContextSetUpException, IOException {
        setContextByContentWithCheck(content);
    }

    @Override
    public String execute(String Xquery) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet;
        resultSet = statement.executeQuery(
                "SELECT ExtractValue('" + xmlDataContent + "','" + Xquery + "');");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        String result = "";
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++)
                result += resultSet.getString(i);
        }
        resultSet.close();
        statement.close();
        return result;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
