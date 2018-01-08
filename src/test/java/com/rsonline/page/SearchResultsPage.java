package com.rsonline.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * This is Search result page class representing search results
 * @author upgundecha
 */
public class SearchResultsPage extends BasePage {

    private WebDriver driver;

    @FindBy(css = "table#results-table tr.resultRow")
    List<WebElement> resultItems;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Get searched items displayed on the page
     * TODO: Enhance this method to work with pagination
     * @return returns list of items displayed on page
     */
    public List<SearchItem> getItems() {
        List<SearchItem> items = new ArrayList<>();

        new WebDriverWait(driver, 30)
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By
                                .cssSelector("table.results-table a.product-name")));

        for(WebElement element : resultItems) {
            String name = element.findElement(By
                    .cssSelector("a.product-name")).getText().trim();
            items.add(new SearchItem(name, element));
        }
        return items;
    }

    /**
     * Get count of products matching the search criteria
     * @return cout of products found
     */
    public String getSearchMatchCount() {
        return driver.findElement(By
                .cssSelector("div.matches span.number"))
                .getText().trim();
    }

    /**
     * Get top level filters
     * @return list of top level filters
     */
    public List<String> getFilters() {
        return getElementTextValues(By
                .cssSelector("div.filter-container a"));
    }

    /**
     * Get top level filters which are disabled
     * @return list of top level filters
     */
    public List<String> getUnavailableFilters() {
        return getElementTextValues(By
                .cssSelector("div.filter-container a.filter-unavailable"));
    }

    /**
     * Apply a filter
     * @param filter top level filter
     * @param values filter values to select. Provide multiple values separated by comma (,)
     * @return Search results page
     */
    public SearchResultsPage applyFilter(String filter, String values) {
        String[] valueArr = values.split(",");

        // select first level filter
        findAndClickElementUsingText(By.cssSelector("div.filter-container a"), filter);

        // select values for the filter
        for(String value : valueArr) {
            findAndClickElementUsingText(By
                    .cssSelector("div.popover div.checkbox label span[ng-class]"), value);
        }
        driver.findElement(By.cssSelector("div.popover rs-apply-button button")).click();

        By locator = By.xpath("//div[@class='appliedAttribute' and starts-with(text(),'" + valueArr[0] + "')]");
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        return this;
    }
}
