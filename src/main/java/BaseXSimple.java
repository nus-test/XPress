import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class BaseXSimple {

    static String username = "admin";
    static String password = "shuxin";
    static String xmlFile = "test1.xml";
    static String[] xqueryFiles = {"xquery.txt"};

    public static String readInputStream(InputStream inputStream) {
        String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        return text;
    }


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
                String xquery = readInputStream(inputStream);
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
