package XPress.DatabaseExecutor;

public class LibXML2ExecutorLow {
    public static String XMLDocumentPath = "C:\\app\\log\\autotest.xml";
    public static String XPath = "//*/preceding::*[(position() <= 2)]";


    static {
        System.loadLibrary("LibXML2ExecutorLow");
    }

    public native String execute(String XMLDocumentPath, String XPath);

    public static void main(String[] args) {
        LibXML2ExecutorLow lib2XMLExecutorLow = new LibXML2ExecutorLow();
        System.out.println(XMLDocumentPath);
        String result = lib2XMLExecutorLow.execute(XMLDocumentPath, XPath);
        System.out.println("Result is: " + result);
    }
}