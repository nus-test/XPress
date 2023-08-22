package XPress.DatatypeControl;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;

public class XMLDatatypeComplexRecorder {
    /**
     * Datatype of result of the current information tree node
     */
    public XMLDatatype xmlDatatype = null;

    /**
     * If tree node represents a sequence, denotes the datatype of the elements in sequence
     */
    public XMLDatatype subDatatype = null;

    /**
     * Only for check usage with a list of nodes. Evaluates to false only if all nodes in sequence (not only for starred node)
     * is ensured has the same dataType. (Also applies when type is single node(Refers to the original context)).
     */
    public boolean nodeMix = true;

    public XMLDatatypeComplexRecorder(XMLDatatype xmlDatatype) {
        this.xmlDatatype = xmlDatatype;
    }

    public XMLDatatypeComplexRecorder(XMLDatatypeComplexRecorder prevRecorder) {
        setData(prevRecorder.xmlDatatype, prevRecorder.subDatatype, prevRecorder.nodeMix);
    }

    public XMLDatatypeComplexRecorder() {}

    public XMLDatatypeComplexRecorder(XMLDatatype xmlDatatype, XMLDatatype subDatatype, boolean nodeMix) {
        setData(xmlDatatype, subDatatype, nodeMix);
    }

    public void setData(XMLDatatype xmlDatatype) {
        this.xmlDatatype = xmlDatatype;
    }

    public void setData(XMLDatatype xmlDatatype, XMLDatatype subDatatype, boolean nodeMix) {
        this.xmlDatatype = xmlDatatype;
        this.subDatatype = subDatatype;
        this.nodeMix = nodeMix;
    }

    public XMLDatatype getActualDatatype() {
        if(xmlDatatype instanceof XMLSequence) return subDatatype;
        return xmlDatatype;
    }
}
