package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.*;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;
import XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction.DirectSubcontextContantNodeGeneration.*;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public enum XMLDirectSubcontext {
    TEXT(1, new TextPredicateTreeNodeGenerator()),
    LAST(2, new LastPredicateTreeNodeGenerator()),
    POSITION(3, new PositionPredicateTreeNodeGenerator()),
    HAS_CHILDREN(4, new HasChildrenConstantNodeGenerator()),
    ATTRIBUTE(5, new AttributePredicateTreeNodeGenerator());

    static int typeCnt = 5;
    int id;
    PredicateTreeNodeFromContextGenerator predicateTreeNodeGenerator;
    static Map<Integer, XMLDirectSubcontext> directSubcontextIdMap = new HashMap<>();

    XMLDirectSubcontext(int id, PredicateTreeNodeFromContextGenerator predicateTreeConstantNodeGenerator) {
        this.id = id;
        this.predicateTreeNodeGenerator = predicateTreeConstantNodeGenerator;
    }

    static {
        for(XMLDirectSubcontext xmlDirectSubcontext : XMLDirectSubcontext.values()) {
            directSubcontextIdMap.put(xmlDirectSubcontext.id, xmlDirectSubcontext);
        }
    }

    public static XMLDatatype getRandomDataType() {
        int dataTypeId = GlobalRandom.getInstance().nextInt(XMLDatatype.typeCnt) + 1;
        return XMLDatatype.datatypeIdMap.get(dataTypeId);
    }

    public PredicateTreeNodeFromContextGenerator getPredicateTreeNodeGenerator() {
        return predicateTreeNodeGenerator;
    }

    public static PredicateTreeConstantNode getDirectSubContext(String XPathPrefix, MainExecutor mainExecutor,
                                                                ContextNode currentNode, boolean allowTextContent)
            throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) {
            int id = GlobalRandom.getInstance().nextInt(typeCnt - 1) + 1;
            if(!allowTextContent && id == 1) id += 1;
            PredicateTreeConstantNode constNode = directSubcontextIdMap.get(id).predicateTreeNodeGenerator
                    .generatePredicateTreeNodeFromContext(currentNode);
            if(id > 1) {
                constNode.dataContent = mainExecutor.executeSingleProcessor(XPathPrefix + "/" + constNode.XPathExpr, "Saxon");
            }
            return constNode;
        } else {
            return XMLDirectSubcontext.ATTRIBUTE.getPredicateTreeNodeGenerator().generatePredicateTreeNodeFromContext(currentNode);
        }
    }
}
