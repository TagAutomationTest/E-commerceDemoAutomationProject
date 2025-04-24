package com.SwagLab.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;


public class BrowserActions {

    private BrowserActions() {
    }

    @Step("Navigate to Url {Url}")
    public static void navigateToUrl(WebDriver driver, String Url) {
        driver.get(Url);
        LogsUtil.info("Redirecting to URL :", Url);


    }

    @Step("Getting current url")
    public static String getCurrentuRL(WebDriver driver) {
        LogsUtil.info("Current URL is : ", driver.getCurrentUrl());
        return driver.getCurrentUrl();

    }

    @Step("Getting page title")
    public static String getPageTitle(WebDriver driver) {
        LogsUtil.info("Page title is : ", driver.getTitle());
        return driver.getTitle();

    }

    @Step
    public static void refreshBrowser(WebDriver driver) {
        LogsUtil.info("Refreshing the browser ");
        driver.navigate().refresh();
    }

    @Step("Closing the browser")
    public static void closeBrowser(WebDriver driver) {
        LogsUtil.info("Closing the browser ");
        driver.quit();


    }
}
