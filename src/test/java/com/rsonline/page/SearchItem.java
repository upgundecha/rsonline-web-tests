package com.rsonline.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This is Search item class representing product listed in search results
 * @author upgundecha
 */
public class SearchItem {

    private WebDriver driver;
    private WebElement parentElem;

    private String name;

    private WebElement qtyElem;
    private WebElement priceElem;
    private WebElement addButton;

    public SearchItem(String name, WebElement parentElem) {
        this.name = name;
        this.parentElem = parentElem;
        this.driver = ((WrapsDriver) parentElem).getWrappedDriver();
        this.driver = driver;
        initialiseElems();
    }

    private void initialiseElems() {
        qtyElem = parentElem.findElement(By.cssSelector("input[name='txtQty']"));
        priceElem = parentElem.findElement(By.cssSelector("span.price"));
        addButton = parentElem.findElement(By.cssSelector("button.addToBasketBtn"));
    }

    public String getName() {
        return name;
    }

    public String getSelectedQty() {
        return qtyElem.getAttribute("value").trim();
    }

    public void setQty(String qtyValue) {
        qtyElem.clear();
        qtyElem.sendKeys(qtyValue);
    }

    public void addItemToCart() {
        addButton.click();
        WebElement basketTitle = driver.findElement(By.cssSelector("div.js-basket a"));
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions
                        .not(ExpectedConditions
                                .attributeToBe(basketTitle, "title", "Your basket is empty")));
    }

    public String getPrice() {
        return  priceElem.getText().trim();
    }
}
