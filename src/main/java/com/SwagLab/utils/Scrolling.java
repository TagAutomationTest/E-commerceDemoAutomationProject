package com.SwagLab.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Scrolling {

    private Scrolling() {

    }

    //Scroll To Element
    @Step("Scrolling to element {locator}")
    public static void scrollToElement(WebDriver driver, By locator) {
        //Code
        LogsUtil.info("Scrolling to element :", locator.toString());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ElementActions.findElement(driver, locator));
    }
}
