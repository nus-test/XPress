package XTest.DatabaseExecutor;

import XTest.CommonUtils;
import XTest.TempTest.MySQLSimple;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public abstract class DatabaseExecutor {
    boolean clearFlag = false;

    public void setContextByFileWithCheck(String pathName) throws SQLException, XMLDBException, IOException, SaxonApiException {
        clearContextWithCheck();
        setContextByFile(pathName);
        clearFlag = true;
    }

    public void setContextByContentWithCheck(String context) throws SQLException, XMLDBException, IOException, SaxonApiException {
        clearContextWithCheck();
        setContextByContent(context);
        clearFlag = true;
    }

    public void setContextByFile(String pathName) throws SQLException, IOException, SaxonApiException, XMLDBException {
        String xmlData =
                CommonUtils.readInputStream(
                        new ByteArrayInputStream(MySQLSimple.class.getResourceAsStream("xmldocs/" + pathName).readAllBytes()));
        setContextByContentWithCheck(xmlData);
    }

    void setContextByContent(String context) throws SaxonApiException, SQLException, XMLDBException, IOException {
        FileWriter writer =
                new FileWriter((this.getClass().getResource("autotest.xml").getPath()));
        writer.write(context);
        System.out.println(context);
        writer.close();
        setContextByFileWithCheck("autotest.xml");
    }

    public void clearContextWithCheck() throws SQLException, XMLDBException, IOException {
        if(clearFlag)
            clearCurrentContext();
    }
    void clearCurrentContext() throws XMLDBException, IOException, SQLException {};
    public abstract String execute(String Xquery) throws IOException, XMLDBException, SaxonApiException, SQLException;
    public abstract void close() throws IOException, XMLDBException, SQLException;
}
