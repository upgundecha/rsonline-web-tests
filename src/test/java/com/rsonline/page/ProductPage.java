package com.rsonline.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This is Product page class
 * @author upgundecha
 */
public class ProductPage extends BasePage {

    private WebDriver driver;

    @FindBy(css = "h1[itemprop='name'")
    private WebElement productName;

    @FindBy(css = "span[itemprop='brand'")
    private WebElement brandName;

    @FindBy(css = "div.price")
    private WebElement price;

    @FindBy(css = "div.qty input")
    private WebElement qty;

    @FindBy(css = "div.btn input")
    private WebElement addToBasketButton;

    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        handleFeedbackPopup();
    }

    public String getName() {
        return productName.getText().trim();
    }

    public String getBrandName() {
        return brandName.getText().trim();
    }

    public String getPrice() {
        return price.getText().trim();
    }

    public String getSelectedQty() {
        return qty.getAttribute("value").trim();
    }

    public void setQty(String qtyValue) {
        qty.clear();
        qty.sendKeys(qtyValue);
    }

    public void addProductToBasket() {
        addToBasketButton.click();

        // wait for product to be added in the shopping cart
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions
                        .not(ExpectedConditions
                                .attributeToBe(addToBasketButton, "class", "loadingBg")));
    }

    public void addProductToBasket(String qtyValue) {
        setQty(qtyValue);
        addProductToBasket();
    }
}
