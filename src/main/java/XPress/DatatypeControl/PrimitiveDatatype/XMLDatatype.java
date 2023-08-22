package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatabaseExecutor.MainExecutor;
import XPress.DatatypeControl.ValueHandler.ValueHandler;
import XPress.DatatypeControl.XMLSimple;
import XPress.DefaultListHashMap;
import XPress.GlobalRandom;
import XPress.GlobalSettings;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class XMLDatatype {
    public static List<XMLDatatype> datatypeList = new ArrayList<>();
    public static List<XMLDatatype> allDatatypeList = new ArrayList<>();
    static Map<Pair<XMLDatatype, XMLDatatype>, Boolean> castableMap = new HashMap<>();
    static DefaultListHashMap<XMLDatatype, XMLDatatype> castableCandidateMap = new DefaultListHashMap<>();
    static Boolean castableMapSet = false;

    public String officialTypeName;
    ValueHandler valueHandler;

    static {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(true);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Datatype.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(
                "XPress.DatatypeControl.PrimitiveDatatype")) {
            try {
                Class c = Class.forName(bd.getBeanClassName());
                Method factoryMethod = c.getDeclaredMethod("getInstance");
                XMLDatatype xmlDatatype = (XMLDatatype) factoryMethod.invoke(null, null);
                allDatatypeList.add(xmlDatatype);
                if(xmlDatatype instanceof XMLDuration && GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1)
                    continue;
                if(!(xmlDatatype instanceof XMLNode) && !(xmlDatatype instanceof XMLSequence) && !(xmlDatatype instanceof XMLMixed))
                    datatypeList.add(xmlDatatype);
            } catch (ClassNotFoundException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *
     * @return Random integrated datatype.
     */
    public static XMLDatatype getRandomDataType() {
        XMLDatatype xmlDatatype = GlobalRandom.getInstance().getRandomFromList(datatypeList);
        if(xmlDatatype instanceof XMLBoolean && GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1)
            xmlDatatype = XMLInteger.getInstance();
        return xmlDatatype;
    }

    public ValueHandler getValueHandler() {
        return valueHandler;
    }

    public static String wrapExpression(String value, XMLDatatype xmlDatatype) {
        if(xmlDatatype instanceof XMLBoolean)
            value += "()";
        else if(xmlDatatype instanceof XMLString)
            value = "\"" + value + "\"";
        else if(xmlDatatype instanceof XMLDuration)
            value = "xs:duration('" + value + "')";
        return value;
    }

    /**
     *
     * @param from
     * @param to
     * @return True if data type "from" castable as "to"
     */
    public static Boolean checkCastable(MainExecutor mainExecutor, XMLDatatype from, XMLDatatype to) throws SQLException, UnexpectedExceptionThrownException, IOException {
        Boolean answer = null;
        Pair<XMLDatatype, XMLDatatype> pair = Pair.of(from, to);
        if(castableMap.containsKey(Pair.of(from, to))) {
            answer = castableMap.get(pair);
        }
        else {
            if(to instanceof XMLNode || (from instanceof XMLNode && !(to instanceof XMLString)))
                answer = false;
            else {
                answer = true;
                for(int i = 1; i <= 3; i ++) {
                    String value = from.getValueHandler().getValue(false);
                    String XPathExpr = wrapExpression(value, from) + " castable as " + to.officialTypeName;
                    String result = mainExecutor.executeSingleProcessor(XPathExpr, GlobalSettings.defaultDBName).strip();
                    if(result.equals("false")) answer = false;
                }
            }
            castableMap.put(pair, answer);
        }
        return answer;
    }

    public static Boolean checkCastableFromMap(XMLDatatype from, XMLDatatype to) {
        Boolean answer = null;
        Pair<XMLDatatype, XMLDatatype> pair = Pair.of(from, to);
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
    public static XMLDatatype getRandomCastableIntegratedDatatype(XMLDatatype datatype) throws DebugErrorException {
        if(castableCandidateMap.get(datatype).isEmpty()) {
            throw new DebugErrorException("Non-castable data type");
        }
        return GlobalRandom.getInstance().getRandomFromList(castableCandidateMap.get(datatype));
    }

    public static void getCastable(MainExecutor mainExecutor) throws SQLException, UnexpectedExceptionThrownException, IOException {
        for(XMLDatatype xmlDatatype : allDatatypeList) {
            if(xmlDatatype instanceof XMLSimple) {
                for(XMLDatatype xmlDatatype2 : allDatatypeList) {
                    if(xmlDatatype2 instanceof XMLSimple) {
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
