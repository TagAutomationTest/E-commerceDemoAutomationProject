package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.utils.CustomSoftAssertion;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    //locators
    private final By productName = By.cssSelector(".inventory_item_name");
    private final By productPrice = By.cssSelector(".inventory_item_price");
    private final By checkoutButton = By.cssSelector(".checkout_button");
    //code
    //variables
    private GUIDriver driver;

    //constructor
    public CartPage(GUIDriver driver) {
        this.driver = driver;
    }

    //actions
    @Step("Get product name")
    private String getProductName() {
        //code
        return driver.element().getText(productName);
    }

    @Step("Get product price")
    private String getProductPrice() {
        //code
        return driver.element().getText(productPrice);
    }

    @Step("Click checkout button")
    public InformationPage clickCheckoutButton() {
        //code
        driver.element().click(checkoutButton);
        return new InformationPage(driver);
    }

    //validations
    @Step("Assert product details")
    public CartPage assertProductDetails(String productName, String productPrice) {
        //code
        String actualProductName = getProductName();
        String actualProductPrice = getProductPrice();
        CustomSoftAssertion.softAssertion.assertEquals(actualProductName, productName, "Product name mismatch");
        CustomSoftAssertion.softAssertion.assertEquals(actualProductPrice, productPrice, "Product price mismatch");
        return this;
    }
}
