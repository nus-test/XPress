package XTest.TestException;

public class UnexpectedExceptionThrownException extends Exception{
    public Exception originalException;
    public UnexpectedExceptionThrownException(Exception originalException) {
        this.originalException = originalException;
    }
}
