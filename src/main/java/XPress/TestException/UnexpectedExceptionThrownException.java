package XPress.TestException;

public class UnexpectedExceptionThrownException extends Exception{
    public Exception originalException;
    public String info;
    public UnexpectedExceptionThrownException(Exception originalException) {
        this.originalException = originalException;
    }

    public UnexpectedExceptionThrownException(Exception originalException, String info) {
        this.originalException = originalException;
        this.info = info;
    }
}