package XPress;

import XPress.CmdArgs.CmdArgs;
import XPress.DatabaseExecutor.DatabaseExecutor;
import XPress.DatabaseExecutor.MainExecutor;
import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.ReportGeneration.ReportManager;
import XPress.TestException.MismatchingResultException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.TestException.XPathVersion1Exception;
import XPress.XMLGeneration.XMLContext;
import XPress.XMLGeneration.XMLDocumentGenerator;
import XPress.XMLGeneration.XMLStructuredDocumentGenerator;
import XPress.XPathGeneration.XPathGenerator;
import com.beust.jcommander.JCommander;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XPressRunner {
    public static void main(String[] argv) throws IOException, SQLException, UnexpectedExceptionThrownException {
        CmdArgs args = new CmdArgs();
        JCommander.newBuilder()
                .addObject(args)
                .build().parse(argv);

        DatabaseExecutor.mapInit();

        List<String> testSystems = Arrays.asList(args.testSystems.split("[\\s;]+"));
        List<String> testSystemArgs = new ArrayList<>();
        for(int i = 0; i < testSystems.size(); i ++) {
            String input = testSystems.get(i);
            String[] info = input.split("\\(");
            testSystems.set(i, info[0]);
            if(info.length > 1) {
                testSystemArgs.add(info[1].replaceAll("\\)+$", ""));
            } else {
                testSystemArgs.add("");
            }
            System.out.println(input + " " + testSystems.get(i) + " " + testSystemArgs.get(i));
        }
        String[] defaultInfo = args.defaultSystem.split("\\(");
        args.defaultSystem = defaultInfo[0];
        args.defaultSystemConfig = defaultInfo.length > 1 ? defaultInfo[1].replaceAll("\\)+$", "") : "";
        GlobalSettings.defaultDBName = args.defaultSystem;
        if(!testSystems.contains(args.defaultSystem)) {
            testSystems.add(args.defaultSystem);
            testSystemArgs.add(args.defaultSystemConfig);
            DatabaseExecutor db = DatabaseExecutor.getExecutor(args.defaultSystem, args.defaultSystemConfig);
            if(db == null) {
                throw new RuntimeException("System " + args.defaultSystem + " not supported");
            }
            db.compareFlag = false;
        } else if(args.defaultSystemConfig.length() > 0) {
            int id = testSystems.indexOf(args.defaultSystem);
            testSystemArgs.set(id, args.defaultSystemConfig);
        }
        ReportManager reportManager = new ReportManager(args.log);
        MainExecutor mainExecutor = new MainExecutor(reportManager);
        for(int i = 0; i < testSystems.size(); i ++) {
            DatabaseExecutor db = DatabaseExecutor.getExecutor(testSystems.get(i), testSystemArgs.get(i));
            if(db == null) {
                throw new RuntimeException("System " + testSystems.get(i) + " not supported");
            }
            db.registerDatabase(mainExecutor);
        }
        GlobalSettings.xPathVersion = args.standard == 3 ?
                GlobalSettings.XPathVersion.VERSION_3 : GlobalSettings.XPathVersion.VERSION_1;

        XMLDatatype.getCastable(mainExecutor);
        int round = args.round;
        XMLDocumentGenerator xmlDocumentGenerator;
        if(GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_3)
            xmlDocumentGenerator = new XMLDocumentGenerator();
        else {
            xmlDocumentGenerator = new XMLStructuredDocumentGenerator();
        }

        try {
            for (int i = 0; i < round; i++) {
                xmlDocumentGenerator.clearContext();
                XMLContext xmlContext = xmlDocumentGenerator.generateXMLContext(50);
                mainExecutor.setExtraLeafNodeContext(xmlDocumentGenerator.generateExtraLeafNodes(15));
                mainExecutor.maxId = mainExecutor.maxIdContainsLeaf = 0;
                XPathGenerator XPathGenerator = new XPathGenerator(mainExecutor);
                System.out.println("------------------ " + i);
                System.out.println(xmlContext.getXmlContent());
                try {
                    int xpathCnt = 200;
                    mainExecutor.setXPathGenerationContext(xmlContext);
                    for (int j = 0; j < xpathCnt; j++) {
                        String XPath = "";
                        try {
                            Pair<List<Pair<Integer, Integer>>, String> XPathResult = XPathGenerator.
                                    getXPathSectionDivided(GlobalRandom.getInstance().nextInt(args.section) + 1);
                            XPath = XPathResult.getRight();
                            if(XPath.length() == 0) continue;
                            mainExecutor.executeAndCompare(XPathResult);
                        } catch (MismatchingResultException | UnexpectedExceptionThrownException e) {
                            XPath = e.toString();
                            System.out.println(XPath);
                            System.out.println(mainExecutor.wrapWithNamespaceDeclaration(XPath));
                            if(e instanceof UnexpectedExceptionThrownException)
                                XPath = ((UnexpectedExceptionThrownException) e).originalException.toString();
                        } catch (XPathVersion1Exception e2) {
                            XPath = "Failed to generate XPath by version 1";
                        }
                        System.out.println("Generated XPath: " + j + " " + XPath);
                    }
                }
                finally {
                    mainExecutor.cleanUp();
                }
            }
        }catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        finally {
            mainExecutor.close();
        }
    }
}
