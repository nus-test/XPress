package XTest.PrimitiveDatatype;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.TestException.UnexpectedExceptionThrownException;
import com.ibm.icu.impl.Pair;
import net.sf.saxon.s9api.SaxonApiException;
import org.apache.xpath.operations.Bool;
import org.xmldb.api.base.XMLDBException;

import javax.xml.transform.Source;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public enum XMLDatatype {
    INTEGER(1, new XMLIntegerHandler()),
    STRING(2, new XMLStringHandler()),
    BOOLEAN(3, new XMLBooleanHandler()),
    DOUBLE(4, new XMLDoubleHandler()),
    DURATION(5, new XMLDurationHandler()),
    NODE(6, new XMLNodeHandler()),
    SEQUENCE(7, new XMLSequenceHandler()),
    MIXED(8, new XMLMixedHandler());

    int id;
    ValueHandler valueHandler;
    public static List<XMLDatatype> dataTypeList = new ArrayList<>();
    /**
     * Only value types of integrated would be generated directly: string, boolean, etc.
     * Non-integrated types could appear as castable types or be constructed through casting: QName, etc.
     * Not all data types are covered.
     * Also, only integrated types would be accepted as function node inputs (Non-integrated types are not implemented).
     */
    public boolean integrated;


    /**
     * Check if 'A castable as B' is true or false.
     */
    static Map<Pair<XMLDatatype, XMLDatatype>, Boolean> castableMap = new HashMap<>();

    private XMLDatatype(int id, ValueHandler valueHandler) {
        this.id = id;
        this.valueHandler = valueHandler;
    }

    static {
        for(XMLDatatype xmlDatatype : XMLDatatype.values()) {
            if(xmlDatatype == DURATION && GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1)
                continue;
            if(xmlDatatype != NODE && xmlDatatype != SEQUENCE && xmlDatatype != MIXED)
                dataTypeList.add(xmlDatatype);
        }
    }

    public static XMLDatatype getRandomDataType() {
        XMLDatatype xmlDatatype = GlobalRandom.getInstance().getRandomFromList(dataTypeList);
        if(xmlDatatype == BOOLEAN && GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1)
            xmlDatatype = INTEGER;
        return xmlDatatype;
    }

    public ValueHandler getValueHandler() {
        return valueHandler;
    }

    public static String wrapExpression(String value, XMLDatatype xmlDatatype) {
        if(xmlDatatype == XMLDatatype.BOOLEAN)
            value += "()";
        else if(xmlDatatype == XMLDatatype.STRING)
            value = "\"" + value + "\"";
        else if(xmlDatatype == XMLDatatype.DURATION)
            value = "xs:duration('" + value + "')";
        return value;
    }

    /**
     *
     * @param from
     * @param to
     * @return True if data type "from" castable as "to"
     */
    public static Boolean checkCastable(MainExecutor mainExecutor, XMLDatatype from, XMLDatatype to) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        Boolean answer = null;
        Pair<XMLDatatype, XMLDatatype> pair = Pair.of(from, to);
        if(castableMap.containsKey(Pair.of(from, to))) {
            answer = castableMap.get(pair);
        }
        else {
            if(to == NODE)
                answer = false;
            else {
                answer = true;
                for(int i = 1; i <= 3; i ++) {
                    String value = from.getValueHandler().getValue(false);
                    String XPathExpr = wrapExpression(value, from) + " castable as " + to.getValueHandler().officialTypeName;
                    String result = mainExecutor.executeSingleProcessor(XPathExpr, GlobalSettings.defaultDBName).strip();
                    if(result.equals("false")) answer = false;
                }
            }
            castableMap.put(pair, answer);
        }
        return answer;
    }

    /**
     *
     * @param datatype
     * @return Random integrated datatype which "datatype" is castable as, no requirements on "datatype":
     * could be non-integrated.
     */
    public static XMLDatatype getRandomCastableIntegratedDatatype(XMLDatatype datatype) {
        return null;
    }

    public static void getCastable(MainExecutor mainExecutor) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException {
        for(XMLDatatype xmlDatatype : XMLDatatype.values()) {
            if(xmlDatatype.getValueHandler() instanceof XMLSimple) {
                for(XMLDatatype xmlDatatype2 : XMLDatatype.values()) {
                    if(xmlDatatype2.getValueHandler() instanceof XMLSimple) {
                        checkCastable(mainExecutor, xmlDatatype, xmlDatatype2);
                    }
                }
            }
        }
    }
}
