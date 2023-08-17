package XPress;
import com.beust.jcommander.Parameter;

public class CmdArgs {

    @Parameter(names = "-log", description = "Place to output log file", required = true)
    public String log;

    @Parameter(names = "-test", description = "Semicolon-separated list of systems to be tested", required = true)
    public String testSystems;

    @Parameter(names = "-default", description = "System used for query generation")
    public String defaultSystem = "Saxon";

    public String defaultSystemConfig = "";

    @Parameter(names = "-standard", description = "XPath standard of tested systems")
    public Integer standard = 3;

    @Parameter(names = "-round", description = "Total round of testing")
    public Integer round = 20;
}

