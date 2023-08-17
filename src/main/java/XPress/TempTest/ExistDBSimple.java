package XPress.TempTest;

import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;
import javax.xml.transform.OutputKeys;
//TODO:eXist problem
//import org.exist.xmldb.EXistResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ExistDBSimple {

    private static String URI = "xmldb:exist://localhost:8089/exist/xmlrpc";
    static String rootDir = "/db";
    private static String collName = "test";
    static String xmlFile = "test1.xml";
    static String[] xqueryFiles = {"xquery.txt"};

    public static String readInputStream(InputStream inputStream) {
        String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        return text;
    }

    public static void main(String args[]) throws Exception {

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource res = null;
        col = DatabaseManager.getCollection(URI + rootDir + "/" + collName);
        col.setProperty(OutputKeys.INDENT, "no");
        res = (XMLResource)col.createResource(xmlFile, XMLResource.RESOURCE_TYPE);
        URL url = ExistDBSimple.class.getResource("/xmldocs/" + xmlFile);
        File f = new File(url.getFile());
        res.setContent(f);
        col.storeResource(res);
        System.out.println("Successfully set content");

        XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
        xqs.setProperty("indent", "yes");

        for(String xqueryFile: xqueryFiles) {
            InputStream inputStream = ExistDBSimple.class.getResourceAsStream("/" + xqueryFile);
            String xquery = readInputStream(inputStream);
            System.out.println("==================Xquery==================");
            System.out.println(xquery);
            System.out.println("==================Execute Xquery==================");
            try {
                CompiledExpression compiled = xqs.compile(xquery);
                ResourceSet result = xqs.execute(compiled);
                ResourceIterator i = result.getIterator();
                Resource resultRes = null;
                while(i.hasMoreResources()) {
                    try {
                        resultRes = i.nextResource();
                        System.out.println(resultRes.getContent());
                    } finally {
                        //TODO:eXist problem
                        //dont forget to cleanup resources
                        //try { ((EXistResource)resultRes).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
                    }
                }
            }catch(Exception e) {
                System.out.println(e);
            }
        }

        col.removeResource(res);
        System.out.println("Content successfully removed");
        if(col != null) {
            try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
        }
    }
}