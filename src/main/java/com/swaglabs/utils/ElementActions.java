package com.swaglabs.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementActions {
    private WebDriver driver;
    //Waits bot
    private Waits waits;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        waits = new Waits(driver);
    }

    //send keys
    @Step("Sending data: {data} to the element: {locator}")
    public void type(By locator, String data) {
        //code
        scrollToElement(locator);
        findElement(locator).sendKeys(data);
        LogsUtil.info("Data entered: ", data, " in the field: ", locator.toString());
    }

    //click
    @Step("Clicking on the element: {locator}")
    public void click(By locator) {
        //code
        scrollToElement(locator);
        findElement(locator).click();
        LogsUtil.info("Clicked on the element: ", locator.toString());
    }

    @Step("Getting text from the element: {locator}")
    public String getText(By locator) {
        waits.waitForElementVisible(locator);
        scrollToElement(locator);
        LogsUtil.info("Getting text from the element: ", locator.toString(), " Text: ", findElement(locator).getText());
        return findElement(locator).getText();
    }

    //find element
    public WebElement findElement(By locator) {
        LogsUtil.info("Finding element: ", locator.toString());
        return driver.findElement(locator);
    }

    public String getTextFromInput(By locator) {
        waits.waitForElementVisible(locator);
        scrollToElement(locator);
        LogsUtil.info("Getting text from the input field: ", locator.toString(), " Text: ", findElement(locator).getDomAttribute("value"));
        return findElement(locator).getDomAttribute("value");
    }

    //scroll to element
    @Step("Scrolling to the element: {0}")
    public void scrollToElement(By locator) {
        //code
        LogsUtil.info("Scrolling to the element: ", locator.toString());
        ((JavascriptExecutor) driver).
                executeScript("arguments[0].scrollIntoView(true);", findElement(locator));
    }
}
