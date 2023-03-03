import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.transform.OutputKeys;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class MultiTester {

    static String username = "admin";
    static String password = "shuxin";
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    static String rootDir = "/db";
    private static String collName = "test";
    static String xmlFile = "test1.xml";
    static String[] xqueryFiles = {"xquery.txt"};


    public static void main(final String... args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
        BaseXClient BaseXSession = new BaseXClient("localhost", 1984, username, password);

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        InputStream xmlData =
                new ByteArrayInputStream(BaseXSimple.class.getResourceAsStream("xmldocs/" + xmlFile).readAllBytes());
        String xmlDataString =
                CommonUtils.readInputStream(
                        new ByteArrayInputStream(MySQLSimple.class.getResourceAsStream("xmldocs/" + xmlFile).readAllBytes()));
        System.out.println(xmlDataString);

        // create new database
        BaseXSession.execute("create db test");
        BaseXSession.add(xmlFile, xmlData);
        System.out.println(BaseXSession.info());

        Collection col = null;
        XMLResource res = null;
        col = DatabaseManager.getCollection(URI + rootDir + "/" + collName);
        col.setProperty(OutputKeys.INDENT, "no");
        res = (XMLResource)col.createResource(xmlFile, XMLResource.RESOURCE_TYPE);
        URL url = ExistDBSimple.class.getResource("xmldocs/" + xmlFile);
        File f = new File(url.getFile());
        res.setContent(f);
        col.storeResource(res);
        XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
        xqs.setProperty("indent", "yes");

        for(String xqueryFile: xqueryFiles) {
            InputStream inputStream = MultiTester.class.getResourceAsStream(xqueryFile);
            String xquery = CommonUtils.readInputStream(inputStream);
            System.out.println("==================Xquery==================");
            System.out.println(xquery);
            System.out.println("==================Execute Xquery BaseX==================");
            try {
                System.out.println(BaseXSession.execute("xquery " + xquery));
            }
            catch(Exception e) {
                System.out.println(e);
                System.out.println("BaseX executed with exception");
            }
            System.out.println("==================Execute Xquery Exist==================");
            try {
                CompiledExpression compiled = xqs.compile(xquery);
                ResourceSet result = xqs.execute(compiled);
                ResourceIterator i = result.getIterator();
                Resource resultRes = null;
                while (i.hasMoreResources()) {
                    try {
                        resultRes = i.nextResource();
                        System.out.println(resultRes.getContent());
                    } finally {
                        //dont forget to cleanup resources
                        try {
                            ((EXistResource) resultRes).freeResources();
                        } catch (XMLDBException xe) {
                            xe.printStackTrace();
                        }
                    }
                }
            }
            catch(Exception e) {
                System.out.println(e);
                System.out.println("Exist executed with exception");
            }
        }
        // run query on database
        BaseXSession.execute("drop db test");
    }
}
