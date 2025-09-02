package com.SwagLab.Pages;

import com.SwagLab.utils.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutOverviewPage {

    //Locators
    private final By finishBtn = By.cssSelector("button#finish");

    //Variables
    private WebDriver driver;

    //Constructor

    public CheckoutOverviewPage(WebDriver driver) {

        this.driver = driver;
    }


    //Actions
    @Step("Click on finish Button")
    public CheckoutConfirmationPage CliclOnFinishButton() {
        ElementActions.clickOnElement(driver, this.finishBtn);
        return new CheckoutConfirmationPage(driver);
    }
}
