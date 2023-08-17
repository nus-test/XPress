package XPress.CmdArgs;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class NodeValidator implements IParameterValidator {
    public void validate(String name, String value)
            throws ParameterException {
        int n = Integer.parseInt(value);
        if (n <= 0) {
            throw new ParameterException("Parameter " + name + " should be greater than 0 (found " + value +")");
        }
        if (n > 100) {
            throw new ParameterException("Parameter " + name + " should be smaller than 100 (found " + value +")");
        }
    }
}

