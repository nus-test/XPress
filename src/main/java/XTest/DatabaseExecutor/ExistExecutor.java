package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.GlobalSettings;
import XTest.TestException.UnsupportedContextSetUpException;
import com.ibm.icu.impl.Pair;
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
import java.util.List;

public class ExistExecutor extends DatabaseExecutor {
    static ExistExecutor existExecutor;
    final String driver = "org.exist.xmldb.DatabaseImpl";
    private static String URI = "xmldb:exist://localhost:8089/exist/xmlrpc";
    static String configDir = "/db/system/config";
    static String rootDir = "/db";
    private String collName = "/test";
    String indexSetupTempStorageFileAddr = "C:\\app\\log\\autotest.xml";

    String configPrefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<collection xmlns=\"http://exist-db.org/collection-config/1.0\">\n" +
            "    <triggers>\n" +
            "        <trigger class=\"org.exist.extensions.exquery.restxq.impl.RestXqTrigger\"/>\n" +
            "    </triggers>";
    String configSuffix = "</collection>";

    Collection collection;
    XMLResource resource;
    XQueryService xqs;
    public ExistExecutor(String collName, String dbName) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.collName = collName;
        init();
        this.dbName = dbName;
    }

    public ExistExecutor() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        init();
    }

    public void init() throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {
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
        setContextByFileLow(collName, fileAddr, true, "autotest.xml");
    }

    public void setContextByFileLow(String collName, String fileAddr, boolean createResource, String resourceName) throws IOException, XMLDBException {
        collection = DatabaseManager.getCollection(URI + rootDir + collName);
        collection.setProperty(OutputKeys.INDENT, "no");
        if(createResource)
            resource = (XMLResource) collection.createResource(resourceName, XMLResource.RESOURCE_TYPE);
        else
            resource = (XMLResource) collection.getResource(resourceName);
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

    public void setRangeIndex(List<Pair<String, String>> contextItems) throws IOException, XMLDBException {
        String indexSetupPrefix = "<index xmlns:mods=\"http://www.loc.gov/mods/v3\">\n";
        String indexSetupSuffix = "</index>";
        String indexSetup = indexSetupPrefix + getRangeIndexConfigString(contextItems) + indexSetupSuffix;
        CommonUtils.writeContextToFile(indexSetup, indexSetupTempStorageFileAddr);
        setContextByFileLow(configDir + rootDir + collName, indexSetupTempStorageFileAddr, false, null);
    }

    public String getRangeIndexConfigString(List<Pair<String, String>> contextItems) {
        String rangeIndexSetupPrefix = "<range>\n";
        String rangeIndexSetupSuffix = "</range>\n";
        String indexSetup = configPrefix + rangeIndexSetupPrefix;
        for(Pair pair: contextItems) {
            String tag = "<create ";
            tag += "qname=\"" + pair.first + "\"";
            tag += "type=\"" + pair.second + "\"";
            tag += "\\>\n";
            indexSetup += tag;
        }
        indexSetup += rangeIndexSetupSuffix + configSuffix;
        return indexSetup;
    }

    public String getNGramIndexConfigString() {
        return null;
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
                resultString += resultRes.getContent() + " ";
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
