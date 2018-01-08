package com.rsonline.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is Base page with common and utility methods
 * @author upgundecha
 */
public class BasePage {

    private WebDriver driver;

    @FindBy(id = "js-basketQty")
    private WebElement basketQty;

    @FindBy(id = "js-basketAmt")
    private WebElement basketAmount;

    @FindBy(css = "i.icon-cart")
    private WebElement shoppingCartIcon;

    private WebElement searchTerm;

    private WebElement btnSearch;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Get Shopping items count
     * @return
     */
    public String getBasketQty() {
        return basketQty.getText().trim();
    }

    /**
     * Get Shopping cart amount
     * @return
     */
    public String getBasketAmount() {
        return basketAmount.getText().trim();
    }

    /**
     * Open Shopping cart from any page
     * @return Shopping Cart Page
     */
    public ShoppingCartPage openShoppingCart() {
        shoppingCartIcon.click();
        return new ShoppingCartPage(driver);
    }

    public SearchResultsPage searchProduct(String keyword) {
        searchTerm.clear();
        searchTerm.sendKeys(keyword);
        btnSearch.click();
        return new SearchResultsPage(driver);
    }

    /**
     * Close feedback popup if displayed
     */
    protected void handleFeedbackPopup() {
        if(driver.findElements(By.xpath("//h1[text()=\"We'd welcome your feedback!\"]")).size() > 0) {
            driver.findElement(By.cssSelector("a[aria-label='Close Modal Box']")).click();
            System.out.println("Closed Feedback Popup");
        }
    }

    /**
     * Find an element using its text and click on it
     * @param locator locator
     * @param value value to find
     */
    protected void findAndClickElementUsingText(By locator, String value) {
        driver.findElements(locator)
                .stream()
                .filter(ele -> ele.getText().trim().equals(value.trim()))
                .findFirst()
                .ifPresent(ele -> ele.click());

    }

    /**
     * Get text values of all elements matching the locator
     * @param locator
     * @return list of text values
     */
    protected List<String> getElementTextValues(By locator) {
        return driver
                .findElements(locator)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
