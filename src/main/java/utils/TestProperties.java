package utils;

import org.apache.commons.lang3.SystemUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestProperties {

    public static final String CHROME = "common.chrome";

    public static final String FIREFOX = "common.firefox";

    public static final String IE = "common.ie";

    public static final String LOCAL = "common.local";

    public static final String URL = "common.url";

    private static boolean chrome;

    private static boolean ie;

    private static boolean fireFox;

    private static boolean local;

    private static String url;

    public static final String TEST_PROPERTIES_FILENAME = System.getProperty("user.dir") + "/src/main/resources/tests.properties";

    public static Map<String, String> propertiesMap;

    private static Boolean isInit = false;

    private static TestProperties instance = null;

    public static TestProperties instance() {
        if (instance == null) {
            instance = new TestProperties();
        }
        return instance;
    }

    private TestProperties() {
        chrome = getBoolean(CHROME, true);
        ie = getBoolean(IE, false);
        fireFox = getBoolean(FIREFOX, false);
        local = getBoolean(LOCAL,true);
        url = getValue(URL);
    }

    static void init() {
        if (!isInit) {
            // Default Values
            propertiesMap = new HashMap<>();
            Properties prop = new Properties();
            FileInputStream input = null;
            try {
                input = new FileInputStream(TEST_PROPERTIES_FILENAME);
                prop.load(input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Map<String, String> tmpPropertiesMap = (Map) prop;
            for (String key : tmpPropertiesMap.keySet()) {
                propertiesMap.put(key, tmpPropertiesMap.get(key));
            }
            propertiesMap = (Map) prop;
            isInit = true;
        }
    }

    public static String getValue(String key) {
        init();
        return propertiesMap.get(key);
    }

    public static String getUrl() {
        init();
        return url;
    }

    public static boolean getBoolean(String key, final boolean defaultResult) {
        init();
        return getPropertyBoolean(key, defaultResult);
    }

    public static boolean getPropertyBoolean(String key, final boolean defaultResult) {
        // ) prop.getProperty(key);
        String result = propertiesMap.get(key);
        if (result == null) {
            return defaultResult;
        }
        return Boolean.parseBoolean(result);
    }

    public static boolean isChrome() {
        return chrome;
    }

    public static boolean isIE() {
        return ie;
    }

    public static boolean isLocal() {
        return local;
    }

    public static boolean isFirefox() {
        return fireFox;
    }
}
