package com.SwagLab.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementActions {

    private ElementActions() {

    }

    //SendKeys
    @Step("Sending data {data} to the element {Locator}")
    public static void sendData(WebDriver driver, By Locator, String data) {
        Waits.waitForElementToBeVisible(driver, Locator);
        Scrolling.scrollToElement(driver, Locator);
        findElement(driver, Locator).sendKeys(data);
        LogsUtil.info("Data entered: ", data, " in the field: ", Locator.toString());

    }

    //GetTextFromWebElement
    @Step("Getting text from element {Locator}")
    public static String GetTextFromWebElement(WebDriver driver, By Locator) {
        Waits.waitForElementToBeVisible(driver, Locator);
        Scrolling.scrollToElement(driver, Locator);
        LogsUtil.info("Get text from : ", Locator.toString(), " Text: ", findElement(driver, Locator).getText());
        return findElement(driver, Locator).getText();
    }

    //GetTextFromWebElement
    @Step("Getting text filled to input")
    public static String GetTextFromInput(WebDriver driver, By Locator) {
        Waits.waitForElementToBeVisible(driver, Locator);
        Scrolling.scrollToElement(driver, Locator);
        LogsUtil.info("input : ", Locator.toString(), "filled by text : " + findElement(driver, Locator).getDomAttribute("value"));
        return findElement(driver, Locator).getDomAttribute("value");
    }

    //ClickOn locator
    @Step("Clicking on element {Locator}")
    public static void clickOnElement(WebDriver driver, By Locator) {
        Waits.waitForElementToBeClickable(driver, Locator);
        Scrolling.scrollToElement(driver, Locator);
        findElement(driver, Locator).click();
        LogsUtil.info("Clicked on the element: ", Locator.toString());

    }

    @Step("Clicking on element using JavascriptExecutor {Locator}")
    public static void clickOnElementByJavaScrip(WebDriver driver, By Locator) {
        Waits.waitForElementToBePresent(driver, Locator);
        Scrolling.scrollToElement(driver, Locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                findElement(driver, Locator));
        LogsUtil.info("Clicked on the element using JavascriptExecutor: ", Locator.toString());

    }


    //findelement
    public static WebElement findElement(WebDriver driver, By Locator) {
        Waits.waitForElementToBeVisible(driver, Locator);
        LogsUtil.info("Finding element : ", Locator.toString());
        return driver.findElement(Locator);

    }

    //findelements
    // @Step("Finding group of elements {Locator}")
    public static List<WebElement> findGroupOfElement(WebDriver driver, By Locator) {
        Waits.waitForListOfElementsToBeVisible(driver, Locator);
        LogsUtil.info("Finding group of element linked by locator : ", Locator.toString());
        return driver.findElements(Locator);

    }

}
