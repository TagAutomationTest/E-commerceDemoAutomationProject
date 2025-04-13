package com.SwagLab.Pages;

import com.SwagLab.utils.ElementActions;
import com.SwagLab.utils.Validations;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutConfirmationPage {
    //Locators
    private final By confirmationMsg = By.xpath("//h2[@data-test='complete-header']");
    private final By homeBtn = By.cssSelector("button#back-to-products");

    //Variables
    private WebDriver driver;

    //Constructor
    public CheckoutConfirmationPage(WebDriver driver) {
        this.driver = driver;
    }

    //Actions
    @Step("Get payment confirmation message")
    public String getConfirmationMessage() {
        return ElementActions.GetTextFromWebElement(driver, this.confirmationMsg);
    }

    @Step("Assert On Confirmation Message : {0}")
    public CheckoutConfirmationPage assertOnConfirmationMessage(String expectedConfirmationMessage) {
        Validations.validateEquals(getConfirmationMessage(), expectedConfirmationMessage, "Payment Confirmation message mismatch");
        return this;
    }
}
