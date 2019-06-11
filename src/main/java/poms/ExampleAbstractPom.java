package poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import utils.LocalDriverManager;
import utils.WebdriverUtils;

public abstract class ExampleAbstractPom {

    protected WebDriver driver = LocalDriverManager.getDriver();

    protected Wait<WebDriver> wait = WebdriverUtils.initializePOM(driver, this);

    protected WebElement wrapper;

    /**
     *  @return boolean , true if wrapper is displayed and false if it is not.
     */
    public abstract boolean isDisplayed();
}
