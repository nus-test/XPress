package XPress.CmdArgs;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class SectionValidator implements IParameterValidator {
    public void validate(String name, String value)
            throws ParameterException {
        int n = Integer.parseInt(value);
        if (n <= 0) {
            throw new ParameterException("Parameter " + name + " should be greater than 0 (found " + value +")");
        }
        if (n > 10) {
            throw new ParameterException("Parameter " + name + " should be smaller than 10 (found " + value +")");
        }
    }
}
