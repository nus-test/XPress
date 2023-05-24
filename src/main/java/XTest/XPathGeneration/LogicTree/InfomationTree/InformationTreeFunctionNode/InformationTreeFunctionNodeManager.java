package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.DefaultListHashMap;
import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLSimple;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode.*;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode.*;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InformationTreeFunctionNodeManager {
    static InformationTreeFunctionNodeManager INSTANCE;

    DefaultListHashMap<XMLDatatype, InformationTreeFunctionNode> simpleRoughContextMatchingMap = new DefaultListHashMap<>();
    DefaultListHashMap<XMLDatatype, InformationTreeFunctionNode> sequenceRoughContextMatchingMap = new DefaultListHashMap<>();
    List<InformationTreeFunctionNode> registeredFunctionList = new ArrayList<>();

    public static InformationTreeFunctionNodeManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new InformationTreeFunctionNodeManager();
            INSTANCE.setupRoughContextMatchingMap();
        }
        return INSTANCE;
    }

    private InformationTreeFunctionNodeManager() {
        registeredFunctionList.add(new BooleanFunctionNode());
        registeredFunctionList.add(new CastableFunctionNode());
        registeredFunctionList.add(new ConcatFunctionNode());
        registeredFunctionList.add(new ContainsFunctionNode());
        registeredFunctionList.add(new DaysFromDurationFunctionNode());
        registeredFunctionList.add(new DoubleAbsFunctionNode());
        registeredFunctionList.add(new DoubleAddFunctionNode());
        registeredFunctionList.add(new DoubleCeilingFunctionNode());
        registeredFunctionList.add(new DoubleDivisionFunctionNode());
        registeredFunctionList.add(new DoubleFloorFunctionNode());
        registeredFunctionList.add(new DoubleMultiplicationFunctionNode());
        registeredFunctionList.add(new DoubleRoundFunctionNode());
        registeredFunctionList.add(new DoubleRoundHalfToEvenFunctionNode());
        registeredFunctionList.add(new DoubleSubtractFunctionNode());
        registeredFunctionList.add(new EndsWithFunctionNode());
        registeredFunctionList.add(new HoursFromDurationFunctionNode());
        registeredFunctionList.add(new IntegerAbsFunctionNode());
        registeredFunctionList.add(new IntegerAddFunctionNode());
        registeredFunctionList.add(new IntegerDivisionFunctionNode());
        registeredFunctionList.add(new IntegerModFunctionNode());
        registeredFunctionList.add(new IntegerMultiplicationFunctionNode());
        registeredFunctionList.add(new IntegerSubtractionFunctionNode());
        registeredFunctionList.add(new LowerCaseFunctionNode());
        registeredFunctionList.add(new MinutesFromDurationFunctionNode());
        registeredFunctionList.add(new MonthsFromDurationFunctionNode());
        registeredFunctionList.add(new NormalizeSpaceFunctionNode());
        registeredFunctionList.add(new NotFunctionNode());
        registeredFunctionList.add(new SecondsFromDurationFunctionNode());
        registeredFunctionList.add(new StartsWithFunctionNode());
        registeredFunctionList.add(new StringLengthFunctionNode());
        registeredFunctionList.add(new SubstringAfterFunctionNode());
        registeredFunctionList.add(new SubstringBeforeFunctionNode());
        registeredFunctionList.add(new SubstringFunctionNode());
        registeredFunctionList.add(new TranslateFunctionNode());
        registeredFunctionList.add(new UpperCaseFunctionNode());
        registeredFunctionList.add(new YearsFromDurationFunctionNode());

        registeredFunctionList.add(new EqualOperatorNode());
        registeredFunctionList.add(new GreaterOrEqualOperatorNode());
        registeredFunctionList.add(new GreaterThanOperatorNode());
        registeredFunctionList.add(new LessOrEqualOperatorNode());
        registeredFunctionList.add(new LessThanOperatorNode());
        registeredFunctionList.add(new NotEqualOperatorNode());

        registeredFunctionList.add(new AttributeFunctionNode());
        registeredFunctionList.add(new HasChildrenFunctionNode());
        registeredFunctionList.add(new LastFunctionNode());
        registeredFunctionList.add(new LocalNameFunctionNode());
        registeredFunctionList.add(new NameFunctionNode());
        registeredFunctionList.add(new PositionFunctionNode());
        registeredFunctionList.add(new TextFunctionNode());
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
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            functionNode = GlobalRandom.getInstance().getRandomFromList(
                    sequenceRoughContextMatchingMap.get(datatypeRecorder.subDatatype)).newInstance();
        }
        else functionNode = GlobalRandom.getInstance().getRandomFromList(
                simpleRoughContextMatchingMap.get(datatypeRecorder.xmlDatatype)).newInstance();
        return functionNode;
    }

    /**
     *
     * @param treeNode
     * @param datatypeRecorder
     * @return A random new information tree function node which could accept the current data type recorded
     * with given "datatypeRecorder". The tree node is attached to the function node as the first child with
     * other contents also filled.
     */
    public InformationTreeFunctionNode getRandomMatchingFunctionNodeWithContentAttached(InformationTreeNode treeNode, XMLDatatypeComplexRecorder datatypeRecorder) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        InformationTreeFunctionNode functionNode = null;
        boolean flag = false;
        while(!flag) {
            functionNode = getRandomMatchingFunctionNode(datatypeRecorder);
            if(functionNode.checkContextAcceptability(treeNode)) {
                functionNode.fillContents(treeNode);
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
        InformationTreeNode dummyNode = new InformationTreeConstantNode();
        dummyNode.context = "10";
        XMLDatatypeComplexRecorder recorder = new XMLDatatypeComplexRecorder();
        for(XMLDatatype xmlDatatype: XMLDatatype.values()) {
            if(xmlDatatype.getValueHandler() instanceof XMLSimple) {
                for(InformationTreeFunctionNode functionNode : registeredFunctionList) {
                    // For single node check
                    recorder.xmlDatatype = xmlDatatype;
                    dummyNode.datatypeRecorder = recorder;
                    if(functionNode.checkContextAcceptability(dummyNode, recorder)) {
                        simpleRoughContextMatchingMap.get(xmlDatatype).add(functionNode);
                    }

                    // For sequence check
                    recorder.setData(XMLDatatype.SEQUENCE, xmlDatatype, true);
                    dummyNode.datatypeRecorder = recorder;
                    if(functionNode.checkContextAcceptability(dummyNode, recorder)) {
                        sequenceRoughContextMatchingMap.get(xmlDatatype).add(functionNode);
                    }
                }
            }
        }
    }
}
