package TempTests;

import java.io.*;

public class BaseXSimple {

    static String username = "admin";
    static String password = "shuxin";
    static String xmlFile = "test1.xml";
    static String[] xqueryFiles = {"xquery.txt"};


    public static void main(final String... args) throws IOException {
        try(BaseXClient session = new BaseXClient("localhost", 1984, username, password)) {
            // define input stream
            final InputStream xmlData =
                    new ByteArrayInputStream(BaseXSimple.class.getResourceAsStream("xmldocs/" + xmlFile).readAllBytes());

            // create new database
            session.execute("create db test");
            session.add(xmlFile, xmlData);
            System.out.println(session.info());
            for(String xqueryFile: xqueryFiles) {
                InputStream inputStream = BaseXSimple.class.getResourceAsStream(xqueryFile);
                String xquery =  CommonUtils.readInputStream(inputStream);
                System.out.println("==================Xquery==================");
                System.out.println(xquery);
                System.out.println("==================Execute Xquery==================");
                System.out.println(session.execute("xquery " + xquery));
            }
            // run query on database
            session.execute("drop db test");
        }
    }
}
