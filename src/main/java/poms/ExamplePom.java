package poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.WebdriverUtils;
import com.shield34.optimizer.sdk.wrappers.Shield34WebElementWrapper;

/**
 * Example pom to search component in wikipedia
 */
public class ExamplePom extends ExampleAbstractPom {

    @FindBy(className = "search-container")
    protected WebElement wrapper;

    protected By searchInput = By.id("searchInput");

    protected By searchIcon = By.className("svg-search-icon");

    public ContenctPom clickSearch() {
        Shield34WebElementWrapper.click(Shield34WebElementWrapper.findElement(wrapper, searchIcon));
        return new ContenctPom();
    }

    public void setSearch(String searchText) {
        Shield34WebElementWrapper.sendKeys(Shield34WebElementWrapper.findElement(wrapper, searchInput), searchText);
    }

    /**
     *  @return boolean , true if wrapper is displayed and false if it is not.
     */
    @Override
    public boolean isDisplayed() {
        return WebdriverUtils.isDisplayed(wrapper);
    }
}
