package TempTests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * This program demonstrates how to make database connection with Oracle
 * database server.
 * @author www.codejava.net
 *
 */
public class OracleSimple {
    static String xmlFile = "test1.xml";
    static String[] xqueryFiles = {"xquery.txt"};

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // registers Oracle JDBC driver - though this is no longer required
            // since JDBC 4.0, but added here for backward compatibility
            Class.forName("oracle.jdbc.OracleDriver");

            // METHOD #2
            String dbURL2 = "jdbc:oracle:thin:@localhost:1521/XE";
            String username = "test";
            String password = "shuxin";
            connection = DriverManager.getConnection(dbURL2, username, password);
            if (connection != null) {
                System.out.println("Connected with connection #2");
            }

            String xmlData =
                    CommonUtils.readInputStream(
                            new ByteArrayInputStream(MySQLSimple.class.getResourceAsStream("xmldocs/" + xmlFile).readAllBytes()));

            Statement statement;
            statement = connection.createStatement();

            String createSQL = "create table test of xmltype";
            String dropSQL = "drop table test";
            //String insertSQL = "insert into test values (XMLType(bfilename('" + xmlPath + "', '" + xmlFile + "'), nls_charset_id('AL32UTF8')))";
            String insertSQL = "insert into test values (XMLType('" + xmlData + "', nls_charset_id('AL32UTF8')))";

            System.out.println("Creating database...");
            statement.execute(createSQL);
            System.out.println("Inserting XML document...");
            statement.execute(insertSQL);

            for(String xqueryFile: xqueryFiles) {
                InputStream inputStream = MultiTester.class.getResourceAsStream(xqueryFile);
                String xquery = CommonUtils.readInputStream(inputStream);
                String selectSQL = "select extract(OBJECT_VALUE, '" + xquery + "') from test";
                System.out.println("==================Xquery==================");
                System.out.println(xquery);
                System.out.println("==================Execute Xquery Oracle==================");
                ResultSet resultSet = statement.executeQuery(selectSQL);
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                while (resultSet.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        String columnValue = resultSet.getString(i);
                        System.out.print(columnValue);
                    }
                    System.out.println("");
                }
                resultSet.close();
            }
            statement.execute(dropSQL);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}