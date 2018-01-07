package com.rsonline.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends BasePage {

    private WebDriver driver;

    @FindBy(css = "div.featured_mfrs div.content_container a")
    private List<WebElement> topBrands;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Map<String, WebElement> getTopBrands() {
        Map<String, WebElement> topBrandsMap = new HashMap<>();
        for(WebElement element : topBrands) {
            topBrandsMap.put(element.getAttribute("title").trim(),
                    element);
        }
        return topBrandsMap;
    }

    public BrandPage selectTopBrand(String name){
        if(getTopBrands().containsKey(name)) {
            WebElement element = getTopBrands().get(name);
            element.click();
            return new BrandPage(driver);
        } else {
            throw new IllegalArgumentException("Specified brand not in the list: " + name);
        }
    }
}
