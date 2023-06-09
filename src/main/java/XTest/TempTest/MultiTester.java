package XTest.TempTest;

import XTest.CommonUtils;
import XTest.DatabaseExecutor.DatabaseExecutor;
import net.sf.saxon.s9api.*;
//TODO:eXist problem
//import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;

public class MultiTester {

    static String username = "admin";
    static String password = "shuxin";
    private static String URI = "xmldb:exist://localhost:8089/exist/xmlrpc";
    static String rootDir = "/db";
    private static String collName = "test";
    static String xmlFile = "test1.xml";
    static String[] xqueryFiles = {"xquery.txt"};


    public static void main(final String... args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, SaxonApiException {
        BaseXClient BaseXSession = new BaseXClient("localhost", 1984, username, password);

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        InputStream xmlData =
                new ByteArrayInputStream(BaseXSimple.class.getResourceAsStream("/xmldocs/" + xmlFile).readAllBytes());
        String xmlDataString =
                CommonUtils.readInputStream(
                        new ByteArrayInputStream(MySQLSimple.class.getResourceAsStream("/xmldocs/" + xmlFile).readAllBytes()));
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
        URL url = MultiTester.class.getResource("/xmldocs/" + xmlFile);
        File f = new File(url.getFile());
        res.setContent(f);
        col.storeResource(res);
        XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
        xqs.setProperty("indent", "yes");

        for(String xqueryFile: xqueryFiles) {
            InputStream inputStream = MultiTester.class.getResourceAsStream("/" + xqueryFile);
            String xquery = CommonUtils.readInputStream(inputStream);
            System.out.println("==================Xquery==================");
            System.out.println(xquery);
            System.out.println("==================Execute Xquery BaseX==================");
            String resultString = "";
            try {
                resultString = BaseXSession.execute("xquery " + xquery);
                System.out.println(resultString);
                System.out.println(DatabaseExecutor.getNodeIdList(resultString));
            }
            catch(Exception e) {
                System.out.println(e);
                System.out.println("BaseX executed with exception");
            }
            resultString = "";
            System.out.println("==================Execute Xquery Exist==================");
            try {
                CompiledExpression compiled = xqs.compile(xquery);
                ResourceSet result = xqs.execute(compiled);
                ResourceIterator i = result.getIterator();
                Resource resultRes = null;
                while (i.hasMoreResources()) {
                    try {
                        resultRes = i.nextResource();
                        //System.out.println(resultRes.getContent());
                        resultString += resultRes.getContent();
                    } finally {
                        //dont forget to cleanup resources
                        //TODO:eXist problem
//                        try {
//                            ((EXistResource) resultRes).freeResources();
//                        } catch (XMLDBException xe) {
//                            xe.printStackTrace();
//                        }
                    }
                }
            }
            catch(Exception e) {
                System.out.println(e);
                System.out.println("Exist executed with exception");
            }
            System.out.println(resultString);
            System.out.println(DatabaseExecutor.getNodeIdList(resultString, "Exist"));
            col.removeResource(res);

            url = SaxonSimple.class.getResource("/" + xqueryFile);
            resultString = "";
            Processor saxon = new Processor(false);

            // compile the query
            XQueryCompiler compiler = saxon.newXQueryCompiler();
            XQueryExecutable exec = compiler.compile(new File(url.getFile()));
            DocumentBuilder builder = saxon.newDocumentBuilder();
            url = SaxonSimple.class.getResource("/xmldocs/" + xmlFile);
            File xmldocFile = new File(url.getFile());

            String xmlFileContent = CommonUtils.readInputStream(new FileInputStream(xmldocFile));
            Source src = new StreamSource(new StringReader(xmlFileContent));
            XdmNode doc = builder.build(src);
            System.out.println("==================Execute Xquery Saxon==================");
            // instantiate the query, bind the input and evaluate
            XQueryEvaluator query = exec.load();
            query.setContextItem(doc);
            XdmValue result = query.evaluate();
            for(int i = 0; i < result.size(); i ++) {
                resultString += result.itemAt(i);
                //System.out.println(result.itemAt(i));
            }
            System.out.println(resultString);
            System.out.println(DatabaseExecutor.getNodeIdList(resultString));
        }
        // run query on database
        BaseXSession.execute("drop db test");
    }
}
