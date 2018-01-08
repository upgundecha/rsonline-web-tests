package com.rsonline.test;

import com.rsonline.model.Filter;
import com.rsonline.page.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

public class StepDefs {

    WebDriver driver;

    HomePage homePage;
    BrandPage brandPage;
    ProductPage productPage;
    ShoppingCartPage shoppingCartPage;
    SearchResultsPage searchResultsPage;
    SearchItem searchItem;

    String productName;
    String price;
    String qty;

    public StepDefs() {
        this.driver = Hooks.getDriver();
    }

    @Given("^user is on home page$")
    public void user_is_on_home_page() throws Throwable {
        driver.get("https://uk.rs-online.com/web/");
        homePage = new HomePage(driver);
    }

    @When("^he selects a top brand from the home page$")
    public void he_selects_a_top_brand_from_the_home_page(Map<String, String> userData) throws Throwable {
        String brand = userData.get("brand");
        assertThat(homePage.getTopBrands()).containsKey(brand);
        brandPage = homePage.selectTopBrand(brand);
        assertThat(brandPage.getBrandTitle()).isEqualTo(brand);
    }

    @When("^selects a popular product from the brand page$")
    public void selects_a_popular_product(Map<String, String> userData) throws Throwable {
        productName = userData.get("product");
        price = userData.get("price");
        qty = userData.get("quantity");

        assertThat(brandPage.getPopularProducts()).containsKey(productName);
        productPage = brandPage.selectPopularProduct(productName);

        assertThat(productPage.getName()).isEqualTo(productName);
        assertThat(productPage.getPrice()).isEqualTo(price);
        assertThat(productPage.getSelectedQty()).isEqualTo(qty);
    }

    @When("^adds the product to shopping cart$")
    public void adds_the_product_to_shopping_cart() throws Throwable {
        productPage.addProductToBasket();
        assertThat(productPage.getBasketQty()).isEqualTo(qty);
        assertThat(productPage.getBasketAmount()).isEqualTo(price);
        shoppingCartPage = productPage.openShoppingCart();

    }

    @Then("^shopping cart should contain the selected product$")
    public void shopping_cart_should_contain_the_selected_product() throws Throwable {
        ShoppingCartLineItem shoppingCartLineItem = shoppingCartPage.getItems().get(0);
        assertThat(shoppingCartLineItem.getSelectedQty()).isEqualTo(qty);
        assertThat(shoppingCartLineItem.getLineAmount()).isEqualTo(price);
    }

    @When("^he search for a product$")
    public void he_search_for_a_product(Map<String, String> userData) throws Throwable {
        productName = userData.get("product");
        price = userData.get("price");
        qty = userData.get("quantity");

        searchResultsPage = homePage.searchProduct(productName);
        searchItem = searchResultsPage.getItems().get(0);

        assertThat(searchItem.getName()).isEqualTo(productName);
        assertThat(searchItem.getPrice()).isEqualTo(price);
        assertThat(searchItem.getSelectedQty()).isEqualTo(qty);
    }

    @When("^adds the product to shopping cart from results page$")
    public void adds_the_product_to_shopping_cart_from_results_page() throws Throwable {
        searchItem.addItemToCart();
        assertThat(searchResultsPage.getBasketQty()).isEqualTo(qty);
        assertThat(searchResultsPage.getBasketAmount()).isEqualTo(price);
        shoppingCartPage = searchResultsPage.openShoppingCart();
    }

    @When("^he search for a category$")
    public void he_search_for_a_category(Map<String, String> userData) throws Throwable {
        searchResultsPage = homePage.searchProduct(userData.get("category"));
    }

    @Then("^results page should list products from the searched category$")
    public void results_page_should_list_products_from_the_searched_category(Map<String, String> userData) throws Throwable {
        assertThat(searchResultsPage.getSearchMatchCount())
                .isEqualToIgnoringCase(userData.get("products_count"));
    }

    @Then("^results page should also display filters$")
    public void results_page_should_also_display_filters(List<String> userData) throws Throwable {
        assertThat(searchResultsPage.getFilters()).isEqualTo(userData);
    }

    @Then("^following filters should be disabled$")
    public void following_filters_should_be_disabled(List<String> userData) throws Throwable {
        assertThat(searchResultsPage.getUnavailableFilters()).isEqualTo(userData);
    }

    @When("^user applies (?:a|additional) filter$")
    public void user_applies_a_filter(List<Filter> filters) throws Throwable {
        for(Filter filter : filters) {
            searchResultsPage.applyFilter(filter.getFilter(), filter.getValue());
        }
    }

    @Then("^results should be narrowed to selected filters$")
    public void results_should_be_narrowed_to_selected_filters(Map<String, String> userData) throws Throwable {
        assertThat(searchResultsPage.getSearchMatchCount())
                .isEqualToIgnoringCase(userData.get("products_count"));
    }
}
