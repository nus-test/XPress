package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.DefaultListHashMap;
import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLSimple;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeContextNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode.*;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode.*;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.*;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode.*;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeGenerator;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InformationTreeFunctionNodeManager {
    static InformationTreeFunctionNodeManager INSTANCE;

    DefaultListHashMap<XMLDatatype, InformationTreeFunctionNode> simpleRoughContextMatchingMap = new DefaultListHashMap<>();
    DefaultListHashMap<XMLDatatype, InformationTreeFunctionNode> sequenceRoughContextMatchingMap = new DefaultListHashMap<>();
    List<InformationTreeFunctionNode> registeredFunctionList = new ArrayList<>();
    int mapLock = 0;

    public static InformationTreeFunctionNodeManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new InformationTreeFunctionNodeManager();
            INSTANCE.setupRoughContextMatchingMap();
        }
        return INSTANCE;
    }

    public void setMapLock() {
        mapLock ++;
    }

    public void unLockMapLock() {
        mapLock --;
    }

    private InformationTreeFunctionNodeManager() {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(true);

        scanner.addIncludeFilter(new AnnotationTypeFilter(FunctionV1.class));
        if(GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_3)
            scanner.addIncludeFilter(new AnnotationTypeFilter(FunctionV3.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(
                "XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode")) {
            try {
                registeredFunctionList.add(Class.forName(bd.getBeanClassName()).
                        asSubclass(InformationTreeFunctionNode.class).newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *
     * @param currentDatatype
     * @return A random datatype which current data type is or is castable into.
     */
    public XMLDatatype getRandomCastableDatatype(XMLDatatype currentDatatype) {
        boolean flag = false;
        XMLDatatype castableDatatype = null;
        while(!flag) {
            castableDatatype = XMLDatatype.getRandomDataType();
            if(XMLDatatype.checkCastableFromMap(currentDatatype, castableDatatype))
                flag = true;
        }
        return castableDatatype;
    }

    public InformationTreeNode getNodeWithSimpleType(XMLDatatype datatype) {
        return getNodeWithSimpleType(datatype, false);
    }

    public InformationTreeNode getNodeWithSimpleType(XMLDatatype datatype, boolean onlyRoot) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.3 && mapLock == 0) {
            int length = InformationTreeGenerator.contextInformationTreeMap.get(datatype).size();
            if(length != 0) {
                int id = GlobalRandom.getInstance().nextInt(length);
                InformationTreeNode treeNode = InformationTreeGenerator.contextInformationTreeMap.get(datatype).get(id);
                InformationTreeGenerator.contextInformationTreeMap.get(datatype).remove(id);
//                try {
//                    System.out.println("Yayyyy" + treeNode.getXPathExpression());
//                }catch(Exception e) {
//
//                }
                return treeNode;
            }
        }
        if(onlyRoot) return null;
        return new InformationTreeConstantNode(datatype, datatype.getValueHandler().getValue(false));
    }

    /**
     *
     * @param currentDatatypeRecorder
     * @return A randomly selected datatype recorder which current input node could be treated as.
     */
    public XMLDatatypeComplexRecorder getRandomTargetedDatatypeRecorder(XMLDatatypeComplexRecorder currentDatatypeRecorder) {
        XMLDatatypeComplexRecorder derivedRecorder = new XMLDatatypeComplexRecorder(currentDatatypeRecorder);
        if(currentDatatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            derivedRecorder.subDatatype = getRandomCastableDatatype(currentDatatypeRecorder.subDatatype);
        }
        else derivedRecorder.xmlDatatype = getRandomCastableDatatype(currentDatatypeRecorder.xmlDatatype);
        return derivedRecorder;
    }

    /**
     *
     * @param datatypeRecorder
     * @return A random new information tree function node which could accept the current data type recorded
     * with given "datatypeRecorder".
     */
    public InformationTreeFunctionNode getRandomMatchingFunctionNode(XMLDatatypeComplexRecorder datatypeRecorder) {
        InformationTreeFunctionNode functionNode;
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            functionNode = GlobalRandom.getInstance().getRandomFromList(
                    sequenceRoughContextMatchingMap.get(datatypeRecorder.getActualDatatype())).newInstance();
        else functionNode = GlobalRandom.getInstance().getRandomFromList(
                simpleRoughContextMatchingMap.get(datatypeRecorder.getActualDatatype())).newInstance();
        return functionNode;
    }

    /**
     *
     * @param treeNode
     * @param datatypeRecorder
     * @return A random new information tree function node which could accept the current data type recorded
     * with given "datatypeRecorder". The tree node is attached to the function node as the first child with
     * other contents also filled (non-randomly).
     */
    public InformationTreeFunctionNode getRandomMatchingFunctionNodeWithContentAttached(InformationTreeNode treeNode, XMLDatatypeComplexRecorder datatypeRecorder) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        return getRandomMatchingFunctionNodeWithContentAttached(treeNode, datatypeRecorder, false, true, true);
    }

    /**
     *
     * @param treeNode
     * @param datatypeRecorder
     * @param random If set to true all contents are filled randomly
     * @return A random new information tree function node which could accept the current data type recorded
     * with given "datatypeRecorder". The tree node is attached to the function node as the first child with
     * other contents also filled.
     */
    public InformationTreeFunctionNode getRandomMatchingFunctionNodeWithContentAttached(
            InformationTreeNode treeNode,
            XMLDatatypeComplexRecorder datatypeRecorder,
            Boolean random, Boolean calculate, Boolean acceptSequenceOperation) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        InformationTreeFunctionNode functionNode = null;
        boolean flag = false;
        while(!flag) {
            functionNode = getRandomMatchingFunctionNode(datatypeRecorder);
            if(functionNode.checkContextAcceptability(treeNode) &&
                    (acceptSequenceOperation ||
                            (!(functionNode instanceof InformationTreeSequenceFunctionNode) && !(functionNode instanceof MapFunctionNode)))) {
                if(!random) functionNode.fillContents(treeNode, calculate);
                else functionNode.fillContentsRandom(treeNode, calculate);
                flag = true;
            }
        }
        return functionNode;
    }

    /**
     * To set up the context matching maps for the purpose of quickly looking up for a function node given datatype.
     * Is only guaranteed to give a likely matching function node, the check against actual node is still needed.
     */
    public void setupRoughContextMatchingMap() {
        InformationTreeContextNode dummyNode = new InformationTreeContextNode();
        dummyNode.getContext().context = "10";
        XMLDatatypeComplexRecorder recorder = new XMLDatatypeComplexRecorder();
        for(InformationTreeFunctionNode functionNode : registeredFunctionList) {
            for(XMLDatatype xmlDatatype: XMLDatatype.values()) {
                if(xmlDatatype.getValueHandler() instanceof XMLSimple) {
                        // For single node check
                        dummyNode.datatypeRecorder.setData(xmlDatatype);
                        if(functionNode.checkContextAcceptability(dummyNode)) {
                            simpleRoughContextMatchingMap.get(xmlDatatype).add(functionNode);
                        }

                        // For sequence check
                        dummyNode.datatypeRecorder.setData(XMLDatatype.SEQUENCE, xmlDatatype, true);
                        if(functionNode.checkContextAcceptability(dummyNode)) {
                            sequenceRoughContextMatchingMap.get(xmlDatatype).add(functionNode);
                        }
                    }
                }
            dummyNode.datatypeRecorder.setData(XMLDatatype.SEQUENCE, XMLDatatype.MIXED, true);
            if(functionNode.checkContextAcceptability(dummyNode)) {
                sequenceRoughContextMatchingMap.get(XMLDatatype.MIXED).add(functionNode);
            }
        }
        //throw new RuntimeException();
    }

    public InformationTreeNode getMapDummyChildNode(InformationTreeNode node) throws DebugErrorException, SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        InformationTreeContextNode dummyChildNode = new InformationTreeContextNode();
        dummyChildNode.XPathExpr = ".";
        dummyChildNode.inheritContextChildInfo(node);
        dummyChildNode.dummyContext = true;
        if(node.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            int id = GlobalRandom.getInstance().nextInt(Integer.parseInt(node.getContext().context)) + 1;
            String calString = "((" + node.getCalculationString() + ")[" + id + "])";
            dummyChildNode.datatypeRecorder = new XMLDatatypeComplexRecorder(node.datatypeRecorder.getActualDatatype());
            if(node.datatypeRecorder.subDatatype == XMLDatatype.NODE) {
                calString = "(" + calString + "/@id cast as xs:integer)";
            } else {
                calString = "(" + calString + " cast as " + node.datatypeRecorder.subDatatype.getValueHandler().officialTypeName + ")";
            }
            dummyChildNode.getContext().setContext(
                    dummyChildNode.getContextInfo().mainExecutor.executeSingleProcessor(calString));
            dummyChildNode.dummyCalculateString = calString;
        }
        else {
            dummyChildNode.datatypeRecorder = new XMLDatatypeComplexRecorder(node.datatypeRecorder);
            dummyChildNode.getContext().setContext(node.context);
            dummyChildNode.dummyCalculateString = "(" + node.getCalculationString() + ")";
        }
        return dummyChildNode;
    }
}
