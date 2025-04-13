package com.SwagLab.Pages;

import com.SwagLab.utils.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage {

    //Locators
    private final By userName = By.cssSelector("input#user-name");
    private final By password = By.cssSelector("input#password");
    private final By loginBtn = By.cssSelector("input#login-button");
    private final By errorMsg = By.cssSelector("h3[data-test='error']");
    private final By productHeader = By.cssSelector("span[class*='title']");

    //Variables
    private WebDriver driver;

    //Constructor
    public SignInPage(WebDriver driver) {
        this.driver = driver;
    }


    //Navigation
    @Step("navigate ToLogin Url ")
    public SignInPage navigateToLoginUrl(String Url) {
        BrowserActions.navigateToUrl(driver, Url);
        return this;
    }

    //Actions
    @Step("Enter the user name : {0} ")
    public SignInPage enterUserName(String usernameValue) {
        ElementActions.sendData(driver, this.userName, usernameValue);
        return this;
    }

    @Step("Enter password : {0} ")
    public SignInPage enterPassword(String passwordValue) {
        ElementActions.sendData(driver, this.password, passwordValue);
        return this;
    }

    @Step("Click on login button")
    public SignInPage clickSubmit() {
        ElementActions.clickOnElement(driver, this.loginBtn);
        return this;
    }

    @Step("Assert login page Url ")
    public SignInPage assertAfterLoginPageUrl(String expected) {
        CustomSoftAssertion.softAssertion.assertEquals(BrowserActions.getCurrentuRL(driver), expected);
        return this;
    }

    @Step("Assert login page title ")
    public SignInPage assertAfterLoginPageTitle(String expected) {
        try {
            CustomSoftAssertion.softAssertion.assertEquals(BrowserActions.getPageTitle(driver), expected);
            LogsUtil.info("assertion passed  : ",
                    "actual page title is :", ElementActions.
                            GetTextFromWebElement(driver, this.productHeader), "and the expected page title is : ", expected);
            return this;
        } catch (AssertionError e) {
            LogsUtil.error("assertion failed  : ", e.getMessage());
            throw e;
        }
    }

    @Step("assert Successful Login ")
    public HomePage assertSuccessfulLogin(String headerValue) {
        try {
            Validations.validateEquals(ElementActions.
                    GetTextFromWebElement(driver, this.productHeader), headerValue, "header validation Failed");
            LogsUtil.info("assertion passed  : ",
                    "actual header is :", ElementActions.
                            GetTextFromWebElement(driver, this.productHeader), "and the expected header is : ", headerValue);
        } catch (AssertionError e) {
            LogsUtil.error("assertion failed  : ", e.getMessage());
            throw e;
        }
        return new HomePage(driver);
    }

    @Step("assert un successful Login using Soft assertion ")
    public HomePage assertSuccessfulLoginSoft(String expectedPageUrl, String expectedPageTitle) {
        assertAfterLoginPageUrl(expectedPageUrl)
                .assertAfterLoginPageTitle(expectedPageTitle);
        return new HomePage(driver);
    }

    //Validations
    @Step("assert un successful Login using hard assertion ")
    public SignInPage assertUnSuccessfulLogin(String errorMessage) {
        Validations.validateTrue(ElementActions.GetTextFromWebElement(driver, this.errorMsg)
                .contains(errorMessage), "error messages not matched");
        return this;
    }


}
