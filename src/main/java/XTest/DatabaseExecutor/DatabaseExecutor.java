package XTest.DatabaseExecutor;

public interface DatabaseExecutor {
    void setContextByFile(String pathName);
    void setContextByContent(String context);
    void execute(String Xquery);
}
