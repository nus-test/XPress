package XTest.DatabaseExecutor;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.transform.OutputKeys;
import java.io.File;
import java.net.URL;

public class ExistExecutor extends DatabaseExecutor {
    static ExistExecutor existExecutor;
    final String driver = "org.exist.xmldb.DatabaseImpl";
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    static String rootDir = "/db";
    private static String collName = "test";
    Collection collection;
    XMLResource resource;
    XQueryService xqs;
    private ExistExecutor() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        dbName = "Exist";
        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
    }

    static public ExistExecutor getInstance() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if(existExecutor == null)
            existExecutor = new ExistExecutor();
        return existExecutor;
    }

    @Override
    public void setContextByFile(String pathName) throws XMLDBException {
        collection = DatabaseManager.getCollection(URI + rootDir + "/" + collName);
        collection.setProperty(OutputKeys.INDENT, "no");
        resource = (XMLResource) collection.createResource(pathName, XMLResource.RESOURCE_TYPE);
        URL url = ExistExecutor.class.getResource("/xmldocs/" + pathName);
        File f = new File(url.getFile());
        resource.setContent(f);
        collection.storeResource(resource);
        xqs = (XQueryService) collection.getService("XQueryService", "1.0");
        xqs.setProperty("indent", "yes");
    }

    @Override
    public void clearCurrentContext() throws XMLDBException {
        collection.removeResource(resource);
    }

    @Override
    public String execute(String Xquery) throws XMLDBException {
        CompiledExpression compiled = xqs.compile(Xquery);
        ResourceSet result = xqs.execute(compiled);
        ResourceIterator i = result.getIterator();
        Resource resultRes = null;
        String resultString = "";
        while (i.hasMoreResources()) {
            try {
                resultRes = i.nextResource();
                resultString += resultRes.getContent();
            } finally {
                //dont forget to cleanup resources
                try {
                    ((EXistResource) resultRes).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return resultString;
    }

    @Override
    public void close() throws XMLDBException {
        collection.close();
    }
}
