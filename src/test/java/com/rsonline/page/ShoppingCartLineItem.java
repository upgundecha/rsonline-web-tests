package com.rsonline.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.PageFactory;

public class ShoppingCartLineItem {

    private WebDriver driver;
    private WebElement parentElem;

    private String name;

    private WebElement qtyElem;
    private WebElement updateLink;
    private WebElement removeLink;
    private WebElement costElem;

    public ShoppingCartLineItem(String name, WebElement parentElem) {
        this.name = name;
        this.parentElem = parentElem;
        this.driver = ((WrapsDriver) parentElem).getWrappedDriver();
        initialiseElems();
    }

    private void initialiseElems() {
        qtyElem = parentElem.findElement(By.cssSelector("td.quantityTd input"));
        updateLink = parentElem.findElement(By.linkText("Update"));
        removeLink = parentElem.findElement(By.linkText("Remove"));
        costElem = parentElem.findElement(By.cssSelector("div.costColumn"));
    }

    public String getName() {
        return name;
    }

    public String getSelectedQty() {
       return qtyElem.getAttribute("value").trim();
    }

    public void updateQty(String qtyValue) {
        qtyElem.clear();
        qtyElem.sendKeys(qtyValue);
        updateLink.click();
    }

    public void removeItem() {
        removeLink.click();
    }

    public String getLineAmount() {
        return costElem.getText().trim();
    }
}
