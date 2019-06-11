package poms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.WebdriverUtils;

public class ContenctPom extends ExampleAbstractPom {

    @FindBy(id = "content")
    protected WebElement wrapper;

    /**
     *  @return boolean , true if wrapper is displayed and false if it is not.
     */
    @Override
    public boolean isDisplayed() {
        return WebdriverUtils.isDisplayed(wrapper);
    }
}
