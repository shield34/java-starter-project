package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import java.util.concurrent.TimeUnit;

/**
 * static class containing webdriver utilities
 *
 * @author
 */
public class WebdriverUtils {

    public static boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static Wait<WebDriver> initializePOM(WebDriver driver, Object pom) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 4), pom);
        return new FluentWait<WebDriver>(driver).withTimeout(50, TimeUnit.SECONDS).pollingEvery(900, TimeUnit.MILLISECONDS);
    }

    public static Wait<WebDriver> initializePOM(WebDriver driver, Object pom, int pollingTime, int elementsTimeout, int waitTimeout) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, elementsTimeout), pom);
        return new FluentWait<WebDriver>(driver).withTimeout(waitTimeout, TimeUnit.SECONDS).pollingEvery(pollingTime, TimeUnit.MILLISECONDS);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
        }
    }
}
