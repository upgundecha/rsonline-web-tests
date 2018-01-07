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
import java.util.stream.Collectors;

public class SearchResultsPage extends BasePage {

    private WebDriver driver;

    @FindBy(css = "table#results-table tr.resultRow")
    List<WebElement> resultItems;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

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

    public String getSearchMatchCount() {
        return driver.findElement(By
                .cssSelector("div.matches span.number"))
                .getText().trim();
    }

    public List<String> getFilters() {
        return driver
                .findElements(By
                        .cssSelector("div.filter-container a"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getUnavailableFilters() {
        return driver
                .findElements(By
                        .cssSelector("div.filter-container a.filter-unavailable"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public SearchResultsPage applyFilter(String filter, String values) {
        String[] valueArr = values.split(",");

        driver.findElements(By
                .cssSelector("div.filter-container a"))
                .stream()
                .filter(ele -> ele.getText().equals(filter))
                .findFirst()
                .ifPresent(ele -> ele.click());

        for(String value : valueArr) {
            driver.findElements(By
                    .cssSelector("div.popover div.checkbox label span[ng-class]"))
                    .stream()
                    .filter(ele -> ele.getText().trim()
                            .equals(value.trim()))
                    .findFirst()
                    .ifPresent(ele -> ele.click());
        }
        driver.findElement(By.cssSelector("div.popover rs-apply-button button")).click();

        By locator = By.xpath("//div[@class='appliedAttribute' and starts-with(text(),'" + valueArr[0] + "')]");
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        return this;
    }
}
