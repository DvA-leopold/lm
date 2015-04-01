package model.versioning;

/**
 * class contains version info of this app,
 * version of this app stored only in this class
 */
public class VersionInfo {
    static {
        version = "0.2.0";
    }

    public static String getVersion() {
        return version;
    }

    private static final String version;
}
