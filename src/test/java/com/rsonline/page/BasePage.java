package com.rsonline.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    public String getBasketQty() {
        return basketQty.getText().trim();
    }

    public String getBasketAmount() {
        return basketAmount.getText().trim();
    }

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

    protected void handleFeedbackPopup() {
        if(driver.findElements(By.xpath("//h1[text()=\"We'd welcome your feedback!\"]")).size() > 0) {
            driver.findElement(By.cssSelector("a[aria-label='Close Modal Box']")).click();
            System.out.println("Closed Feedback Popup");
        }
    }
}
