package XTest;

public class GlobalSettings {
    static GlobalSettings globalSettings;

    public enum XPathVersion {
        VERSION_1,
        VERSION_3
    }

    public static XPathVersion xPathVersion = XPathVersion.VERSION_1;

    public static String defaultDBName = "Saxon";
    public static Boolean debugOutput = false;

    private GlobalSettings(){};

    public GlobalSettings getInstance() {
        if(globalSettings == null) globalSettings = new GlobalSettings();
        return globalSettings;
    }

}

