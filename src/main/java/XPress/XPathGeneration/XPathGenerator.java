package XPress.XPathGeneration;

import XPress.TestException.DebugErrorException;
import XPress.TestException.MismatchingResultException;
import XPress.TestException.UnexpectedExceptionThrownException;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface XPathGenerator {
    public Pair<List<Pair<Integer, Integer>>, String> getXPathSectionDivided (int depth)
            throws SQLException, IOException, UnexpectedExceptionThrownException, DebugErrorException;
    public String getXPath(int depth)
            throws SQLException, MismatchingResultException, IOException, InstantiationException, IllegalAccessException, UnexpectedExceptionThrownException, DebugErrorException;
}
