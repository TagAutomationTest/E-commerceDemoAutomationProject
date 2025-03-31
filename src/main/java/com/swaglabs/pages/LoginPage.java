package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.utils.CustomSoftAssertion;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.swaglabs.utils.PropertiesUtils.getPropertyValue;


public class LoginPage {
    //Variables
    private final GUIDriver driver;
    //Locators
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    //Constructor
    public LoginPage(GUIDriver driver) {
        this.driver = driver;
    }

    //Navigate to the login page
    @Step("Navigate to the login page")
    public void navigateToLoginPage() {
        driver.browser().navigateToURL("https://www.saucedemo.com/");
    }

    //Actions
    @Step("Enter username: {0}")
    public LoginPage enterUsername(String username) {
        driver.element().type(this.username, username);
        return this;
    }

    @Step("Enter password: {0}")
    public LoginPage enterPassword(String password) {
        driver.element().type(this.password, password);
        return this;
    }

    @Step("Click login button")
    public LoginPage clickLoginButton() {
        driver.element().click(loginButton);
        return this;
    }

    //Getters
    @Step("Get error message")
    public String getErrorMessage() {
        return driver.element().getText(errorMessage);
    }


    //Validations
    @Step("Assert login page URL")
    public LoginPage assertLoginPageURL() {
        CustomSoftAssertion.softAssertion.assertEquals(driver.browser().getCurrentURL(), getPropertyValue("homeURL"), "URL is not as expected");
        return this;
    }

    @Step("Assert login page title")
    public LoginPage assertLoginPageTitle() {
        CustomSoftAssertion.softAssertion.assertEquals(driver.browser().getPageTitle(), getPropertyValue("appTitle"), "Title is not as expected");
        return this;
    }

    @Step("Assert successful login")
    public LoginPage assertSuccessfulLoginSoft() {
        assertLoginPageURL().assertLoginPageTitle();
        return this;
    }

    @Step("Assert successful login")
    public HomePage assertSuccessfulLogin() {
        driver.validate().validatePageUrl(getPropertyValue("homeURL"));
        return new HomePage(driver);
    }

    @Step("Assert unsuccessful login")
    public HomePage assertUnsuccessfulLogin() {
        driver.validate().validateEquals(getErrorMessage(), getPropertyValue("errorMSG"), "Error message is not as expected");
        return new HomePage(driver);
    }

}
