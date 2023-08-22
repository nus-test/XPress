package XPress.DatabaseExecutor;

import XPress.GlobalSettings;
import XPress.TestException.UnsupportedContextSetUpException;
import java.io.IOException;
import java.sql.*;

public class PgExecutor extends DatabaseExecutor{
    static PgExecutor pgExecutor;
    String url = "jdbc:postgresql://localhost:5432/test";
    String username = "postgres";
    String password = "shuxin";
    Connection connection;
    Statement statement;
    String xmlDataContent;

    private PgExecutor(String config) throws SQLException {
        dbName = "PostgreSQL";
        if(config.length() > 0) {
            String[] configs = config.split("[\\s,]+");
            username = configs[0];
            password = configs[1];
        }
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
        dbXPathVersion = GlobalSettings.XPathVersion.VERSION_1;
    }

    public static void registerMap() {
        nameExecutorMap.put("PostgreSQL", PgExecutor.class);
    }

    static public PgExecutor getInstance() throws SQLException, ClassNotFoundException {
        return getInstance("");
    }

    static public PgExecutor getInstance(String config) throws SQLException, ClassNotFoundException {
        if(pgExecutor == null)
            pgExecutor = new PgExecutor(config);
        return pgExecutor;
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
    public void setContextByContentLow(String content) {
        xmlDataContent = content;
    }

    @Override
    public void clearCurrentContext() {
        xmlDataContent = null;
    }

    @Override
    public String execute(String Xquery) throws SQLException {
        String selectSQL = "select xpath('" + Xquery + "', '" + xmlDataContent + "');";;
        ResultSet resultSet = statement.executeQuery(selectSQL);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        String result = "";
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                Array data = resultSet.getArray(i);
                Object[] data2 = (Object[]) data.getArray();
                for(Object obj : data2) {
                    result += obj.toString();
                }
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

