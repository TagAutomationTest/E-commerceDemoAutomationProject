package com.SwagLab.Pages;

import com.SwagLab.utils.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class CartPage {


    private WebDriver driver;

    //Locators
    private final By productName = By.cssSelector(".inventory_item_name");
    private final By productPrice = By.cssSelector(".inventory_item_price");
    private final By CheckoutBtn = By.cssSelector("button#checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }


    //actions
    @Step("get Product Name ")
    private String getProductName() {
        //code
        LogsUtil.info("product name is : " + ElementActions.GetTextFromWebElement(driver, productName));
        return ElementActions.GetTextFromWebElement(driver, productName);
    }

    @Step("get Product price ")
    private String getProductPrice() {
        //code
        LogsUtil.info("product Price is : " + ElementActions.GetTextFromWebElement(driver, productPrice));
        return ElementActions.GetTextFromWebElement(driver, productPrice);
    }

    @Step("Validate product details already added to cart")
    public CartPage assertProductDetails(String expectedItemName, String expectedItemPrice) {
        String actualProductName = getProductName();
        String actualProductPrice = getProductPrice();
        CustomSoftAssertion.softAssertion.assertEquals(actualProductName, expectedItemName, "product name mismatch");
        CustomSoftAssertion.softAssertion.assertEquals(actualProductPrice, expectedItemPrice, "product price mismatch");
        LogsUtil.info("product details validated properly");
        return this;
    }

    @Step("Click on check out")
    public CheckoutInformationPage checkoutItem() {
        LogsUtil.info("Clicking on CheckoutBtn --->>");
        ElementActions.clickOnElement(driver, this.CheckoutBtn);
        return new CheckoutInformationPage(driver);
    }
}
