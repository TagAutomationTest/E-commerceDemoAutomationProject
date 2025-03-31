package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.utils.CustomSoftAssertion;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class InformationPage {
    //locators
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");
    //code
    //variables
    private GUIDriver driver;

    //constructor
    public InformationPage(GUIDriver driver) {
        this.driver = driver;
    }

    //actions
    @Step("Fill information form: First Name: {0}, Last Name: {1}, Postal Code: {2}")
    public InformationPage fillInformationForm(String firstName, String lastName, String postalCode) {
        //code
        driver.element().type(this.firstName, firstName);
        driver.element().type(this.lastName, lastName);
        driver.element().type(this.postalCode, postalCode);
        return this;
    }

    @Step("Click continue button")
    public OverviewPage clickContinueButton() {
        //code
        driver.element().click(continueButton);
        return new OverviewPage(driver);
    }


    //validations
    @Step("Assert information page")
    public InformationPage assertInformationPage(String firstName, String lastName, String postalCode) {
        //code
        CustomSoftAssertion.softAssertion.assertEquals(driver.element().getTextFromInput(this.firstName), firstName);
        CustomSoftAssertion.softAssertion.assertEquals(driver.element().getTextFromInput(this.lastName), lastName);
        CustomSoftAssertion.softAssertion.assertEquals(driver.element().getTextFromInput(this.postalCode), postalCode);
        return this;
    }

}
