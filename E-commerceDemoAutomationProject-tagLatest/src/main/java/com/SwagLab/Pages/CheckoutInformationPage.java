package com.SwagLab.Pages;

import com.SwagLab.utils.BrowserActions;
import com.SwagLab.utils.CustomSoftAssertion;
import com.SwagLab.utils.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutInformationPage {
    //locators
    private final By firstName = By.cssSelector("input#first-name");
    private final By lastName = By.cssSelector("input#last-name");
    private final By postalCode = By.cssSelector("input#postal-code");
    private final By continueBtn = By.cssSelector("input#continue");

    //Variables
    private WebDriver driver;

    //Constructor
    public CheckoutInformationPage(WebDriver driver) {
        this.driver = driver;
    }

    //Action
    @Step("fill Checkout Information")
    public CheckoutInformationPage fillCheckoutInformation(String firstName, String Lastname, String postalCod) {
        ElementActions.sendData(driver, this.firstName, firstName);
        ElementActions.sendData(driver, this.lastName, Lastname);
        ElementActions.sendData(driver, this.postalCode, postalCod);
        return this;
    }

    @Step("Click on Continue Button")
    public CheckoutOverviewPage clickOnContinue() {
        ElementActions.clickOnElement(driver, this.continueBtn);
        return new CheckoutOverviewPage(driver);
    }

    //Validation
    @Step("Assert on data filled")
    public CheckoutInformationPage assertOnFilledData(String firstName, String Lastname, String postalCod) {
        CustomSoftAssertion.softAssertion.assertEquals(ElementActions.GetTextFromInput(driver, this.firstName), firstName);
        CustomSoftAssertion.softAssertion.assertEquals(ElementActions.GetTextFromInput(driver, this.lastName), Lastname);
        CustomSoftAssertion.softAssertion.assertEquals(ElementActions.GetTextFromInput(driver, this.postalCode), postalCod);
        return this;
    }

}

