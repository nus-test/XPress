package XTest.XPathGeneration;

import XTest.DatabaseExecutor.DatabaseExecutor;
import XTest.GlobalRandom;
import XTest.XMLGeneration.ContextNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.checkerframework.checker.units.qual.C;
import org.xmldb.api.base.XMLDBException;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XPathGenerator {
    Map<Integer, ContextNode> contextNodeMap = new HashMap<>();
    List<DatabaseExecutor> databaseExecutorList = new ArrayList<>();

    XPathGenerator() {
    }

    void setXPathGenerationContext(ContextNode root, String xmlDataContent) throws IOException, SQLException, XMLDBException, SaxonApiException {
        contextNodeMap = new HashMap<>();
        getContextNodeMap(root);
        for(DatabaseExecutor databaseExecutor : databaseExecutorList) {
            databaseExecutor.setContextByContentWithCheck(xmlDataContent);
        }
    }

    void getContextNodeMap(ContextNode currentNode) {
        contextNodeMap.put(currentNode.id, currentNode);
        for(ContextNode childNode : currentNode.childList)
            getContextNodeMap(childNode);
    }

    void registerDatabase(DatabaseExecutor databaseExecutor) {
        databaseExecutorList.add(databaseExecutor);
    }

    String generateXPath(String currentBuilder, List<ContextNode> currentNodeList, int depth) {
        if(depth == 0) {
            return currentBuilder;
        }
        String builder = currentBuilder;
        // First stage

        return null;
    }

    String getXPath(int depth) {
        generateXPath("", null, depth);
        return null;
    }
}
