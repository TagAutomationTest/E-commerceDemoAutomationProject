package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ConfirmationPage {
    //code
    //locators
    private final By confirmationMessage = By.cssSelector(".complete-header");
    //variables
    private GUIDriver driver;

    //constructor
    public ConfirmationPage(GUIDriver driver) {
        this.driver = driver;
    }

    //actions
    @Step("Get confirmation message")
    public String getConfirmationMessage() {
        return driver.element().getText(confirmationMessage);
    }

    //validations
    @Step("Assert confirmation message: {0}")
    public void assertConfirmationMessage(String expectedMessage) {
        String actualMessage = getConfirmationMessage();
        driver.validate().validateEquals(actualMessage, expectedMessage, "Confirmation message mismatch");
    }
}
