package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.utils.LogsUtil;
import com.swaglabs.utils.PropertiesUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.locators.RelativeLocator;

public class HomePage {
    //locators
    private final By cartIcon = By.cssSelector("[data-test='shopping-cart-link']");
    //code
    //variables
    private GUIDriver driver;

    //constructor
    public HomePage(GUIDriver driver) {
        this.driver = driver;
    }

    //actions
    @Step("Navigate to home page")
    public HomePage navigateToHomePage() {
        //code
        driver.browser().navigateToURL(PropertiesUtils.getPropertyValue("homeURL"));
        return this;
    }

    @Step("Add specific product to cart")
    public HomePage addSpecificProductToCart(String productName) {
        //code
        LogsUtil.info("Adding " + productName + " to cart");
        By addToCartButton = RelativeLocator.with(By.tagName("button")).below(By.xpath("//div[.='" + productName + "']"));
        driver.element().click(addToCartButton);
        return this;
    }

    @Step("Click cart icon")
    public CartPage clickCartIcon() {
        //code
        driver.element().click(cartIcon);
        return new CartPage(driver);
    }

    //validations
    @Step("Assert product added to cart")
    public HomePage assertProductAddedToCart(String productName) {
        //code
        By addToCartButton = RelativeLocator.with(By.tagName("button")).below(By.xpath("//div[.='" + productName + "']"));
        String actualValue = driver.element().getText(addToCartButton);
        LogsUtil.info("Actual value: " + actualValue);
        driver.validate().validateEquals(actualValue, "Remove", "Product not added to cart");
        LogsUtil.info(productName + " added to cart successfully");
        return this;
    }
}
