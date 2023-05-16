package XTest.TestException;

public class DebugErrorException extends Exception {
    String debugErrorMessage;

    public DebugErrorException(){}

    public DebugErrorException(String debugErrorMessage) {
        this.debugErrorMessage = debugErrorMessage;
    }
}
