package com.swaglabs.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Validations {
    private WebDriver driver;
    private BrowserActions browserActions;


    public Validations(WebDriver driver) {
        this.driver = driver;
        browserActions = new BrowserActions(driver);
    }

    @Step("Validate True")
    public void validateTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Step("Validate False")
    public void validateFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    @Step("Validate Equals")
    public void validateEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    @Step("Validate Not Equals")
    public void validateNotEquals(String actual, String expected, String message) {
        Assert.assertNotEquals(actual, expected, message);
    }

    @Step("Validate Page Url: {expected}")
    public void validatePageUrl(String expected) {
        Assert.assertEquals(browserActions.getCurrentURL(), expected);
    }

    @Step("Validate Page Title: {expected}")
    public void validatePageTitle(String expected) {
        Assert.assertEquals(browserActions.getPageTitle(), expected);
    }
}
