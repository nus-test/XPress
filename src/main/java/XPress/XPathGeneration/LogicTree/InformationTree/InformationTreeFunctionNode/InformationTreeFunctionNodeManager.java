package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.DatatypeControl.PrimitiveDatatype.XMLMixed;
import XPress.DatatypeControl.PrimitiveDatatype.XMLNode;
import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.DefaultListHashMap;
import XPress.GlobalRandom;
import XPress.GlobalSettings;
import XPress.DatatypeControl.XMLDatatypeComplexRecorder;
import XPress.DatatypeControl.XMLSimple;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.TestException.XPathVersion1Exception;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeContextNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.*;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeGenerator;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                "XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode")) {
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

    public InformationTreeNode getNodeWithSimpleValueOrSequence(XMLDatatype datatype) {
        InformationTreeNode node = getNodeWithSequenceType(datatype);
        double prob = GlobalRandom.getInstance().nextDouble();
        if(node != null && prob < 0.6) return node;
        return getNodeWithSimpleType(datatype);
    }

    public InformationTreeNode getNodeWithAnySequence(XMLDatatype datatype) {
        InformationTreeNode node = getNodeWithSequenceType(XMLMixed.getInstance());
        double prob = GlobalRandom.getInstance().nextDouble();
        if(node != null && prob < 0.3) return node;
        return getNodeWithSequenceType(datatype);
    }

    public InformationTreeNode getNodeWithSequenceType(XMLDatatype datatype) {
        return getNodeWithType(datatype, true, true);
    }

    public InformationTreeNode getNodeWithSimpleType(XMLDatatype datatype) {
        return getNodeWithSimpleType(datatype, false);
    }

    public InformationTreeNode getNodeWithSimpleType(XMLDatatype datatype, boolean onlyRoot) {
        return getNodeWithType(datatype, false, onlyRoot);
    }

    public InformationTreeNode getNodeWithType(XMLDatatype datatype, boolean isSequence, boolean onlyRoot) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.3 && mapLock == 0) {
            int length = isSequence ? InformationTreeGenerator.sequenceInformationTreeMap.get(datatype).size() :
                    InformationTreeGenerator.contextInformationTreeMap.get(datatype).size();
            if(length != 0) {
                int id = GlobalRandom.getInstance().nextInt(length);
                InformationTreeNode treeNode = isSequence ? InformationTreeGenerator.sequenceInformationTreeMap.get(datatype).get(id) :
                        InformationTreeGenerator.contextInformationTreeMap.get(datatype).get(id);
                InformationTreeGenerator.contextInformationTreeMap.get(datatype).remove(id);
                return treeNode;
            }
        }
        if(onlyRoot) return null;
        if(isSequence) {
            if(datatype instanceof XMLNode) return null;
            return new InformationTreeConstantNode(new XMLDatatypeComplexRecorder(XMLSequence.getInstance(), datatype, false),
                    datatype.getValueHandler().getSequenceValue(datatype));
        }
        return new InformationTreeConstantNode(datatype, datatype.getValueHandler().getValue(false));
    }

    /**
     *
     * @param currentDatatypeRecorder
     * @return A randomly selected datatype recorder which current input node could be treated as.
     */
    public XMLDatatypeComplexRecorder getRandomTargetedDatatypeRecorder(XMLDatatypeComplexRecorder currentDatatypeRecorder) {
        XMLDatatypeComplexRecorder derivedRecorder = new XMLDatatypeComplexRecorder(currentDatatypeRecorder);
        if(currentDatatypeRecorder.xmlDatatype instanceof XMLSequence) {
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
        if(datatypeRecorder.xmlDatatype instanceof XMLSequence)
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
    public InformationTreeFunctionNode getRandomMatchingFunctionNodeWithContentAttached(InformationTreeNode treeNode, XMLDatatypeComplexRecorder datatypeRecorder) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        return getRandomMatchingFunctionNodeWithContentAttached(treeNode, datatypeRecorder,
                !GlobalSettings.starNodeSelection,  GlobalSettings.starNodeSelection, true);
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
            Boolean random, Boolean calculate, Boolean acceptSequenceOperation) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        InformationTreeFunctionNode functionNode = null;
        boolean flag = false;
        int cnt = 0;
        while(!flag) {
            functionNode = getRandomMatchingFunctionNode(datatypeRecorder);
            if(functionNode.checkContextAcceptability(treeNode) &&
                    (acceptSequenceOperation ||
                            (!(functionNode instanceof InformationTreeSequenceFunctionNode) && !(functionNode instanceof MapFunctionNode)))) {
                if(!random) functionNode.fillContents(treeNode, calculate);
                else functionNode.fillContentsRandom(treeNode, calculate);
                flag = true;
            }
            cnt ++;
            if(GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1 && cnt > 50)
                throw new XPathVersion1Exception();
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
            for(XMLDatatype xmlDatatype: XMLDatatype.allDatatypeList) {
                if(xmlDatatype instanceof XMLSimple) {
                        // For single node check
                        dummyNode.datatypeRecorder.setData(xmlDatatype);
                        if(functionNode.checkContextAcceptability(dummyNode)) {
                            simpleRoughContextMatchingMap.get(xmlDatatype).add(functionNode);
                        }

                        // For sequence check
                        dummyNode.datatypeRecorder.setData(XMLSequence.getInstance(), xmlDatatype, true);
                        if(functionNode.checkContextAcceptability(dummyNode)) {
                            sequenceRoughContextMatchingMap.get(xmlDatatype).add(functionNode);
                        }
                    }
                }
            dummyNode.datatypeRecorder.setData(XMLSequence.getInstance(), XMLMixed.getInstance(), true);
            if(functionNode.checkContextAcceptability(dummyNode)) {
                sequenceRoughContextMatchingMap.get(XMLMixed.getInstance()).add(functionNode);
            }
        }
        //throw new RuntimeException();
    }

    public InformationTreeNode getMapDummyChildNode(InformationTreeNode node) throws DebugErrorException, SQLException, UnexpectedExceptionThrownException, IOException {
        InformationTreeContextNode dummyChildNode = new InformationTreeContextNode();
        dummyChildNode.XPathExpr = ".";
        dummyChildNode.inheritContextChildInfo(node);
        dummyChildNode.dummyContext = true;
        if(node.datatypeRecorder.xmlDatatype instanceof XMLSequence) {
            Integer id = GlobalSettings.starNodeSelection ? GlobalRandom.getInstance().nextInt(Integer.parseInt(node.getContext().context)) + 1 : null;
            String calString = "((" + node.getCalculationString() + ")[" + id + "])";
            dummyChildNode.datatypeRecorder = new XMLDatatypeComplexRecorder(node.datatypeRecorder.getActualDatatype());
            if(node.datatypeRecorder.subDatatype instanceof XMLNode) {
                calString = "(" + calString + "/@id cast as xs:integer)";
            } else {
                calString = "(" + calString + " cast as " + node.datatypeRecorder.subDatatype.officialTypeName + ")";
            }
            if(GlobalSettings.starNodeSelection)
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
