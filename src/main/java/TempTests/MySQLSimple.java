package TempTests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;

public class MySQLSimple {
    static String xmlFile = "test1.xml";
    static String[] xqueryFiles = {"xquery.txt"};
    public static void main(String arg[])
    {
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test?useTimezone=true&serverTimezone=UTC",
                    "root", "shuxin");
            String xmlData =
                    CommonUtils.readInputStream(
                            new ByteArrayInputStream(MySQLSimple.class.getResourceAsStream("xmldocs/" + xmlFile).readAllBytes()));
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            for(String xqueryFile: xqueryFiles) {
                InputStream inputStream = MultiTester.class.getResourceAsStream(xqueryFile);
                String xquery = CommonUtils.readInputStream(inputStream);
                resultSet = statement.executeQuery(
                        "SELECT ExtractValue('" + xmlData + "','" + xquery + "');");
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
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    } // function ends
} // class ends