package core;

import org.testng.annotations.BeforeClass;
import com.shield34.optimizer.sdk.wrappers.Shield34WebDriverWrapper;

public class ExampleSeleniumBaseTest extends SeleniumBaseTest {

    protected String url = testProperties.getUrl();

    /**
     * Creating webdriver and navigating to url
     */
    @BeforeClass
    public void init() {
        exampleBeforeClass();
        Shield34WebDriverWrapper.get(driver, url);
    }
}
