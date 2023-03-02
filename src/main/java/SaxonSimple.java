//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.net.URL;
//import javax.xml.xquery.XQConnection;
//import javax.xml.xquery.XQDataSource;
//import javax.xml.xquery.XQException;
//import javax.xml.xquery.XQPreparedExpression;
//import javax.xml.xquery.XQResultSequence;
//import com.saxonica.xqj.SaxonXQDataSource;
//public class SaxonSimple {
//    static String[] xqueryFiles = {"sxquery.txt"};
//    public static void main(String[] args){
//        try {
//            execute();
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch (XQException e) {
//            e.printStackTrace();
//        }
//    }
//    private static void execute() throws FileNotFoundException, XQException{
//        for(String xqueryFile: xqueryFiles) {
//            URL url = SaxonSimple.class.getResource("saxdocs/" + xqueryFile);
//            File f = new File(url.getFile());
//            InputStream inputStream = new FileInputStream(f);
//            XQDataSource ds = new SaxonXQDataSource();
//            XQConnection conn = ds.getConnection();
//            XQPreparedExpression exp = conn.prepareExpression(inputStream);
//            XQResultSequence result = exp.executeQuery();
//            while (result.next()) {
//                System.out.println(result.getItemAsString(null));
//            }
//        }
//    }
//}