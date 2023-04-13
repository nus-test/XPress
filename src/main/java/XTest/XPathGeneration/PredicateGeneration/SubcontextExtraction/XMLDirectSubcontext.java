package XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.*;
import XTest.ReportGeneration.KnownBugs;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XMLGeneration.AttributeNode;
import XTest.XMLGeneration.ContextNode;
import XTest.XMLGeneration.ElementNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;
import XTest.XPathGeneration.PredicateGeneration.SubcontextExtraction.DirectSubcontextContantNodeGeneration.*;
import net.sf.saxon.s9api.SaxonApiException;
import org.basex.query.value.item.Int;
import org.basex.query.value.item.Str;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public enum XMLDirectSubcontext {
    TEXT(1, new TextPredicateTreeNodeGenerator()),
    LAST(2, new LastPredicateTreeNodeGenerator()),
    POSITION(3, new PositionPredicateTreeNodeGenerator()),
    HAS_CHILDREN(4, new HasChildrenConstantNodeGenerator()),
    ATTRIBUTE(5, new AttributePredicateTreeNodeGenerator());

    static List<XMLDirectSubcontext> directSubContextListWithoutAttr = new ArrayList<>();
    static int typeCnt = 5;
    int id;
    PredicateTreeNodeFromContextGenerator predicateTreeNodeGenerator;;

    XMLDirectSubcontext(int id, PredicateTreeNodeFromContextGenerator predicateTreeConstantNodeGenerator) {
        this.id = id;
        this.predicateTreeNodeGenerator = predicateTreeConstantNodeGenerator;
    }

    static {
        for(XMLDirectSubcontext directSubcontext : XMLDirectSubcontext.values()) {
            if(directSubcontext == ATTRIBUTE)
                continue;
            if(KnownBugs.exist && KnownBugs.exist4824 && (directSubcontext == POSITION || directSubcontext == LAST)) {
                continue;
            }
            directSubContextListWithoutAttr.add(directSubcontext);
        }
    }

    public PredicateTreeNodeFromContextGenerator getPredicateTreeNodeGenerator() {
        return predicateTreeNodeGenerator;
    }


    // XPath prefix: original XPath /*
    public static PredicateTreeConstantNode getDirectSubContext(String XPathPrefix, MainExecutor mainExecutor,
                                                                ContextNode currentNode, boolean allowTextContent)
            throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.3 && currentNode.childWithLeafList.size() != 0 && !KnownBugs.exist) {
            String pathToLeaf = currentNode.getStrPathToSpecifiedLeafNode();
            double probNode = GlobalRandom.getInstance().nextDouble();
            if(probNode < 0.3) {
                return new PredicateTreeConstantNode(XMLDatatype.NODE, null, pathToLeaf);
            }
            String currentNodeIdentifier = "//*[@id=\"" + currentNode.id + "\"]";
            String XPathExpr = currentNodeIdentifier + "/" + pathToLeaf;
            List<Integer> resultList = mainExecutor.executeSingleProcessorGetIdList(XPathExpr);
            prob = GlobalRandom.getInstance().nextDouble();
            ContextNode starredNode;
            if(prob < 0.5) {
                String tempXPathExpr = XPathExpr;
                int cnt = 0;
                int originalSize = resultList.size();
                resultList = new ArrayList<>();
                while(resultList.size() == 0 && cnt <= 3){
                    int id = GlobalRandom.getInstance().nextInt(originalSize) + 1;
                    if(cnt == 3) id = 1;
                    XPathExpr = tempXPathExpr + "[" + id + "]";
                    resultList = mainExecutor.executeSingleProcessorGetIdList(XPathExpr);
                    cnt += 1;
                }
            }
            if(resultList.size() == 0) {
                System.out.println("------------------------------->");
                System.out.println(XPathExpr);
                System.out.println(XPathPrefix);
                System.out.println("********************************");
            }
            starredNode = mainExecutor.contextNodeMap.get(GlobalRandom.getInstance().getRandomFromList(resultList));
            XPathExpr = XPathExpr.substring(currentNodeIdentifier.length() + 1);
            XPathExpr = "(" + XPathExpr + ")[" + (GlobalRandom.getInstance().nextInt(resultList.size()) + 1) + "]";
            prob = GlobalRandom.getInstance().nextDouble();
            if(prob < 0.6) {
                ElementNode subContextNode = getAttrOrText(starredNode);
                String subContextName;
                if(subContextNode instanceof AttributeNode)
                    subContextName = "@" + subContextNode.tagName;
                else subContextName = "text()";
                PredicateTreeConstantNode predicateTreeConstantNode =
                        new PredicateTreeConstantNode(subContextNode, XPathExpr + "/" + subContextName);
                return predicateTreeConstantNode;
            }
            return new PredicateTreeConstantNode(starredNode, XPathExpr);
        }
        prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.3) {
            XMLDatatype xmlDatatype = XMLDatatype.getRandomDataType();
            if(xmlDatatype == XMLDatatype.DURATION) xmlDatatype = XMLDatatype.INTEGER;
            String content = xmlDatatype.getValueHandler().getValue(false);
            String XPathExpr = content;
            if(xmlDatatype == XMLDatatype.STRING)
                XPathExpr = "\"" + XPathExpr + "\"";
            if(xmlDatatype == XMLDatatype.BOOLEAN)
                XPathExpr += "()";
            XPathExpr = "(" + XPathExpr + ")";
            return new PredicateTreeConstantNode(xmlDatatype, content, XPathExpr);
        }
        prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5 || GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1) {
            XMLDirectSubcontext subContextType = GlobalRandom.getInstance().getRandomFromList(directSubContextListWithoutAttr);
            if(GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1 &&
                    subContextType == HAS_CHILDREN)
                subContextType = POSITION;
            if(!allowTextContent && subContextType == TEXT) {
                if(!KnownBugs.exist || !KnownBugs.exist4824) subContextType = POSITION;
                else subContextType = HAS_CHILDREN;
            }
            PredicateTreeNodeFromContextGenerator predicateTreeNodeFromContextGenerator = subContextType.predicateTreeNodeGenerator;
            PredicateTreeConstantNode constNode = predicateTreeNodeFromContextGenerator
                    .generatePredicateTreeNodeFromContext(currentNode);
            if(subContextType != TEXT) {
                String result = mainExecutor.executeSingleProcessor(
                        predicateTreeNodeFromContextGenerator.getSubContentXPathGenerator(XPathPrefix, currentNode), "Saxon");
                constNode.dataContent = constNode.datatype == XMLDatatype.INTEGER ?
                        Integer.toString(XMLIntegerHandler.parseInt(result)) : result;
            }
            return constNode;
        } else {
            return XMLDirectSubcontext.ATTRIBUTE.getPredicateTreeNodeGenerator().generatePredicateTreeNodeFromContext(currentNode);
        }
    }

    public static ElementNode getAttrOrText(ContextNode currentNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.3 || GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1) return currentNode;
        return GlobalRandom.getInstance().getRandomFromList(currentNode.attributeList);
    }
}
