package core;

import com.shield34.optimizer.sdk.extensions.InitializeProxyExtension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.os.ExecutableFinder;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.TestProperties;
import java.io.File;
import com.shield34.optimizer.sdk.extensions.EnableBrowserLoggingExtension;

public class SeleniumDriver {

    static String exePath;

    /**
     * Locating webdriver location
     *
     * @param exeName     driver name
     * @param exeProperty driver Property
     */
    private static void fetchDriverPath(String exeName, String exeProperty) {
        String defaultPath = new ExecutableFinder().find(exeName);
        exePath = System.getProperty(exeProperty, defaultPath);
        if (exePath == null) {
            String[] paths = { System.getenv(exeProperty), System.getProperty("user.dir") + "/drivers" };
            for (String p : paths) {
                File file;
                if (Platform.getCurrent().is(Platform.WINDOWS)) {
                    file = new File(p + File.separator + exeName + ".exe");
                    if (file.canExecute()) {
                        exePath = file.getAbsolutePath();
                        break;
                    }
                }
                if (Platform.getCurrent().is(Platform.MAC)) {
                    file = new File(p + File.separator + exeName + "-for-macosx");
                    if (file.canExecute()) {
                        exePath = file.getAbsolutePath();
                        break;
                    }
                }
                if (Platform.getCurrent().is(Platform.LINUX)) {
                    file = new File(p + File.separator + exeName + "-for-linux");
                    if (file.exists()) {
                        exePath = file.getAbsolutePath();
                        break;
                    }
                }
            }
        }
        System.setProperty(exeProperty, exePath);
        if (!(new File(exePath)).exists()) {
            String msg = "Could not find driver. " + System.getProperty(exeProperty);
            throw new RuntimeException(msg);
        }
    }

    /**
     * Selecting Driver
     *
     * @param browser browser name
     */
    public static void setDriverProperty(String browser) {
        switch(browser.toLowerCase()) {
            case BrowserType.CHROME:
                fetchDriverPath("chromedriver", "webdriver.chrome.driver");
                break;
            case BrowserType.FIREFOX:
                fetchDriverPath("geckodriver", "webdriver.gecko.driver");
                break;
            case BrowserType.IE:
                fetchDriverPath("IEDriverServer", "webdriver.ie.driver");
                break;
            default:
                throw new RuntimeException("Browser is not correct");
        }
    }

    /**
     * Creating web driver
     *
     * @param browser      browser name
     * @param capabilities web driver capabilities
     */
    public static WebDriver createDriver(String browser, DesiredCapabilities capabilities) {
        // setDriverProperty(browser);
        TestProperties sDKTestProperties = TestProperties.instance();
        WebDriver driver = null;
        File dummyFile = new File("");
        File file;
        switch(browser.toLowerCase()) {
            case BrowserType.CHROME:
                if (sDKTestProperties.isLocal()) {
                    if (capabilities == null) {
                        capabilities = DesiredCapabilities.chrome();
                    }
                    ChromeOptions options = new ChromeOptions();
                    // if (!sDKTestProperties.isLocal())
                    options.addArguments("--headless");
                    options.addArguments("--start-maximized", "--disable-translate", "--disable-gpu", "--verbose", "--privileged");
                    options.addArguments("--test-type");
                    // overcome limited resource problems
                    options.addArguments("--disable-dev-shm-usage");
                    // applicable to windows os only
                    options.addArguments("--disable-gpu");
                    // Bypass OS security model
                    options.addArguments("--no-sandbox");
                    // disabling extensions
                    options.addArguments("--disable-extensions");
                    InitializeProxyExtension.addProxyToCapabilities(capabilities);
                    options.setCapability("goog:chromeOptions", capabilities);
                    String fileName = exePath;
                    file = new File(fileName);
                    ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(file).usingAnyFreePort().build();
                    try {
                        driver = new ChromeDriver(service, options);
                    } catch (WebDriverException e) {
                        e.printStackTrace();
                    }
                } else {
                // driver = new RemoteWebDriver(new URL(node), capabilities);
                }
                break;
            case BrowserType.FIREFOX:
                if (capabilities == null) {
                    capabilities = DesiredCapabilities.firefox();
                }
                if (sDKTestProperties.isLocal()) {
                    EnableBrowserLoggingExtension.enableDriverLogging(capabilities);
                    InitializeProxyExtension.addProxyToCapabilities(capabilities);
                    driver = new FirefoxDriver(capabilities);
                } else {
                // driver = new RemoteWebDriver(new URL(node), capabilities);
                }
                break;
            case BrowserType.IE:
                if (sDKTestProperties.isLocal()) {
                    DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
                    capabilitiesIE.setCapability("requireWindowFocus", true);
                    capabilitiesIE.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, false);
                    capabilitiesIE.setCapability("ie.ensureCleanSession", true);
                    driver = new InternetExplorerDriver(capabilitiesIE);
                } else {
                    DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
                // driver = new RemoteWebDriver(new URL(node), capability);
                }
                break;
        }
        return driver;
    }
}
