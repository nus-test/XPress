package XTest.TempTest;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xquery.XQException;

import net.sf.saxon.s9api.*;

public class SaxonSimple {
    static String[] xqueryFiles = {"xquery.txt"};
    static String xmlFile = "test1.xml";
    public static void main(String[] args){
        try {
            execute();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (XQException | IOException | SaxonApiException e) {
            e.printStackTrace();
        }
    }

    public static String readInputStream(InputStream inputStream) {
        String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        return text;
    }

    private static void execute() throws IOException, XQException, SaxonApiException {
        for(String xqueryFile: xqueryFiles) {
            System.out.println("==================Xquery==================");
            String xqueryFilePath = xqueryFile;
            URL url = SaxonSimple.class.getResource("/" + xqueryFilePath);
            String xquery = readInputStream(SaxonSimple.class.getResourceAsStream("/" + xqueryFilePath));
            System.out.println(xquery);
            // the Saxon processor object
            Processor saxon = new Processor(false);

            // compile the query
            XQueryCompiler compiler = saxon.newXQueryCompiler();
            XQueryExecutable exec = compiler.compile(new File(url.getFile()));

            // parse the string as a document node
            DocumentBuilder builder = saxon.newDocumentBuilder();
            url = SaxonSimple.class.getResource("/xmldocs/" + xmlFile);
            File xmldocFile = new File(url.getFile());

            String xmlFileContent = readInputStream(new FileInputStream(xmldocFile));
            Source src = new StreamSource(new StringReader(xmlFileContent));
            XdmNode doc = builder.build(src);
            System.out.println("==================Execute Xquery==================");
            // instantiate the query, bind the input and evaluate
            XQueryEvaluator query = exec.load();
            query.setContextItem(doc);
            XdmValue result = query.evaluate();
            for(int i = 0; i < result.size(); i ++) {
                System.out.println(result.itemAt(i));
            }
        }
    }
}