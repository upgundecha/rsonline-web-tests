package com.rsonline.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * This is Shopping cart page class
 * @author upgundecha
 */
public class ShoppingCartPage extends BasePage {

    private WebDriver driver;

    @FindBy(css = "table.cartTable tr.dataRow.lineRow")
    private List<WebElement> productsInCart;

    @FindBy(css = "td.orderValueCell")
    private WebElement orderValueElem;


    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<ShoppingCartLineItem> getItems() {
        List<ShoppingCartLineItem> items = new ArrayList<>();
        for(WebElement element : productsInCart) {
            String name = element.findElement(By.cssSelector("a.link2")).getText().trim();
            items.add(new ShoppingCartLineItem(name, element));
        }
        return items;
    }
}
