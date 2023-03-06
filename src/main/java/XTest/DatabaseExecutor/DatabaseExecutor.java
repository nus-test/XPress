package XTest.DatabaseExecutor;

import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public interface DatabaseExecutor {
    void setContextByFile(String pathName) throws IOException, XMLDBException, SaxonApiException, SQLException;
    void setContextByContent(String context) throws SaxonApiException, SQLException;
    void clearCurrentContext() throws XMLDBException, IOException, SQLException;
    String execute(String Xquery) throws IOException, XMLDBException, SaxonApiException, SQLException;
    void close() throws IOException, XMLDBException, SQLException;
}
