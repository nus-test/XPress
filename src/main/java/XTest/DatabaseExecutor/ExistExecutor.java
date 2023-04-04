package XTest.DatabaseExecutor;

import XTest.GlobalSettings;
import XTest.TestException.UnsupportedContextSetUpException;
import net.sf.saxon.s9api.SaxonApiException;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.transform.OutputKeys;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;

public class ExistExecutor extends DatabaseExecutor {
    static ExistExecutor existExecutor;
    final String driver = "org.exist.xmldb.DatabaseImpl";
    private static String URI = "xmldb:exist://localhost:8089/exist/xmlrpc";
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
        dbXPathVersion = GlobalSettings.XPathVersion.VERSION_3;
        DatabaseManager.registerDatabase(database);
    }

    static public ExistExecutor getInstance() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if(existExecutor == null)
            existExecutor = new ExistExecutor();
        return existExecutor;
    }

    @Override
    void setContextWithCheck(String content, String fileAddr) throws SQLException, UnsupportedContextSetUpException, XMLDBException, IOException, SaxonApiException {
        setContextByFileWithCheck(fileAddr);
    }

    public void setContext(String info) throws SQLException, XMLDBException, IOException, SaxonApiException, UnsupportedContextSetUpException {
        super.setContextByFile(info);
    }

    @Override
    public void setContextByFileLow(String fileAddr) throws IOException, XMLDBException {
        collection = DatabaseManager.getCollection(URI + rootDir + "/" + collName);
        collection.setProperty(OutputKeys.INDENT, "no");
        resource = (XMLResource) collection.createResource("autotest", XMLResource.RESOURCE_TYPE);
        File f = new File(fileAddr);
        resource.setContent(f);
        collection.storeResource(resource);
        xqs = (XQueryService) collection.getService("XQueryService", "1.0");
        xqs.setProperty("indent", "yes");
    }

    @Override
    public void setContextByContentLow(String content) throws UnsupportedContextSetUpException {
        throw new UnsupportedContextSetUpException();
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
