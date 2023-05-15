package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.NumericalBinaryOperator;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class InformationTreeFunctionNodeManager {
    /**
     *
     * @param currentDatatype
     * @return A random datatype which current data type is or is castable into.
     */
    public static XMLDatatype getRandomCastableDatatype(XMLDatatype currentDatatype) {
        return null;
    }

    /**
     *
     * @param currentDatatypeRecorder
     * @return A randomly selected datatype recorder which current input node could be treated as.
     */
    public static XMLDatatypeComplexRecorder getRandomTargetedDatatypeRecorder(XMLDatatypeComplexRecorder currentDatatypeRecorder) {
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
    public static InformationTreeFunctionNode getRandomMatchingFunctionNode(XMLDatatypeComplexRecorder datatypeRecorder) {
        return null;
    }

    /**
     *
     * @param treeNode
     * @param datatypeRecorder
     * @return A random new information tree function node which could accept the current data type recorded
     * with given "datatypeRecorder". The tree node is attached to the function node as the first child with
     * other contents also filled.
     */
    public static InformationTreeFunctionNode getRandomMatchingFunctionNodeWithContentAttached(InformationTreeNode treeNode, XMLDatatypeComplexRecorder datatypeRecorder) {
        InformationTreeFunctionNode functionNode = getRandomMatchingFunctionNode(datatypeRecorder);
        functionNode.fillContents(treeNode);
        return functionNode;
    }

    public static String wrapNumericalBinaryFunctionExpr(InformationTreeNode childNode, InformationTreeNode currentNode, boolean returnConstant) {
        if(childNode instanceof NumericalBinaryOperator && childNode.getClass() != currentNode.getClass()) {
            return "(" + childNode.getXPathExpression(returnConstant) + ")";
        }
        return childNode.getXPathExpression(returnConstant);
    }
}
