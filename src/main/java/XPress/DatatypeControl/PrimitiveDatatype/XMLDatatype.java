package XPress.DatatypeControl;

import XPress.DatabaseExecutor.MainExecutor;
import XPress.DefaultListHashMap;
import XPress.GlobalRandom;
import XPress.GlobalSettings;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class XMLDatatype {
    public static List<XMLDatatype> dataTypeList = new ArrayList<>();
    static Map<Pair<XMLDatatype, XMLDatatype>, Boolean> castableMap = new HashMap<>();
    static DefaultListHashMap<XMLDatatype, XMLDatatype> castableCandidateMap = new DefaultListHashMap<>();
    static Boolean castableMapSet = false;

    ValueHandler valueHandler;


    /**
     *
     * @return Random integrated datatype.
     */
    public static XMLDatatype getRandomDataType() {
        XMLDatatype xmlDatatype = GlobalRandom.getInstance().getRandomFromList(dataTypeList);
        if(xmlDatatype instanceof XML_Boolean && GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1)
            xmlDatatype = XML_Integer.getInstance();
        return xmlDatatype;
    }

    public ValueHandler getValueHandler() {
        return valueHandler;
    }

    public static String wrapExpression(String value, XMLDatatype xmlDatatype) {
        if(xmlDatatype instanceof XML_Boolean)
            value += "()";
        else if(xmlDatatype instanceof XML_String)
            value = "\"" + value + "\"";
        else if(xmlDatatype instanceof XML)
            value = "xs:duration('" + value + "')";
        return value;
    }

    /**
     *
     * @param from
     * @param to
     * @return True if data type "from" castable as "to"
     */
    public static Boolean checkCastable(MainExecutor mainExecutor, XMLDatatype_t from, XMLDatatype_t to) throws SQLException, UnexpectedExceptionThrownException, IOException {
        Boolean answer = null;
        Pair<XMLDatatype_t, XMLDatatype_t> pair = Pair.of(from, to);
        if(castableMap.containsKey(Pair.of(from, to))) {
            answer = castableMap.get(pair);
        }
        else {
            if(to == NODE || (from == NODE && to != STRING))
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

    public static Boolean checkCastableFromMap(XMLDatatype_t from, XMLDatatype_t to) {
        Boolean answer = null;
        Pair<XMLDatatype_t, XMLDatatype_t> pair = Pair.of(from, to);
        if(castableMap.containsKey(Pair.of(from, to))) {
            answer = castableMap.get(pair);
        }
        return answer;
    }

    /**
     *
     * @param datatype
     * @return Random integrated datatype which "datatype" is castable as, no requirements on "datatype":
     * could be non-integrated.
     */
    public static XMLDatatype_t getRandomCastableIntegratedDatatype(XMLDatatype_t datatype) throws DebugErrorException {
        if(castableCandidateMap.get(datatype).isEmpty()) {
            throw new DebugErrorException("Non-castable data type");
        }
        return GlobalRandom.getInstance().getRandomFromList(castableCandidateMap.get(datatype));
    }

    public static void getCastable(MainExecutor mainExecutor) throws SQLException, UnexpectedExceptionThrownException, IOException {
        for(XMLDatatype_t xmlDatatype : XMLDatatype_t.values()) {
            if(xmlDatatype.getValueHandler() instanceof XMLSimple) {
                for(XMLDatatype_t xmlDatatype2 : XMLDatatype_t.values()) {
                    if(xmlDatatype2.getValueHandler() instanceof XMLSimple) {
                        boolean castable = checkCastable(mainExecutor, xmlDatatype, xmlDatatype2);
                        if(castable) {
                            castableCandidateMap.get(xmlDatatype).add(xmlDatatype2);
                        }
                    }
                }
            }
        }
        castableMapSet = true;
    }
}
