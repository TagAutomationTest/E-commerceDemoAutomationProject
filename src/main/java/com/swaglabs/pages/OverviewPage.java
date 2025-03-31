package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class OverviewPage {
    //code
    //locators
    private final By finishButton = By.id("finish");
    //variables
    private GUIDriver driver;

    //constructor
    public OverviewPage(GUIDriver driver) {
        this.driver = driver;
    }

    //actions
    @Step("Click finish button")
    public ConfirmationPage clickFinishButton() {
        //code
        driver.element().click(finishButton);
        return new ConfirmationPage(driver);
    }

}
