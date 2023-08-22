package XPress;

public class GlobalSettings {
    static GlobalSettings globalSettings;

    public enum XPathVersion {
        VERSION_1,
        VERSION_3
    }

    public static XPathVersion xPathVersion = XPathVersion.VERSION_3;

    public static Boolean useNamespace = true;

    public static String defaultDBName = "Saxon";
    public static Boolean debugOutput = false;

    public static Boolean starNodeSelection = false;
    public static Boolean targetedParameter = true;
    public static Boolean rectifySelection = true;

    private GlobalSettings(){};

    public GlobalSettings getInstance() {
        if(globalSettings == null) globalSettings = new GlobalSettings();
        return globalSettings;
    }

}