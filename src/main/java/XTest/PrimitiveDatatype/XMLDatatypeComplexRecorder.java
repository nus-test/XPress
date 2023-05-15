package XTest.PrimitiveDatatype;

public class XMLDatatypeComplexRecorder {
    /**
     * Datatype of result of the current information tree node
     */
    public XMLDatatype xmlDatatype;

    /**
     * If tree node represents a sequence, denotes the datatype of the elements in sequence
     */
    public XMLDatatype subDatatype;

    /**
     * Only for check usage with a list of nodes. Evaluates to false only if all nodes in sequence (not only for starred node)
     * is ensured has the same dataType.
     */
    public boolean nodeMix = true;

    public XMLDatatypeComplexRecorder(XMLDatatype xmlDatatype) {
        this.xmlDatatype = xmlDatatype;
    }

    public XMLDatatypeComplexRecorder(XMLDatatypeComplexRecorder prevRecorder) {
        this.xmlDatatype = prevRecorder.xmlDatatype;
        this.subDatatype = prevRecorder.subDatatype;
        this.nodeMix = prevRecorder.nodeMix;
    }

    public XMLDatatypeComplexRecorder() {}
}
