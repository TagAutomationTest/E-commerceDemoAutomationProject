package com.swaglabs.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class BrowserActions {
    private WebDriver driver;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Navigating to URL: {url}")
    public void navigateToURL(String url) {
        driver.get(url);
        LogsUtil.info("Navigated to URL: ", url);
    }

    //Get current URL
    @Step("Getting current URL")
    public String getCurrentURL() {
        LogsUtil.info("Current URL: ", driver.getCurrentUrl());
        return driver.getCurrentUrl();

    }

    //Get page title
    @Step("Getting page title")
    public String getPageTitle() {
        LogsUtil.info("Page title: ", driver.getTitle());
        return driver.getTitle();
    }

    //refresh page
    @Step("Refreshing the page")
    public void refreshPage() {
        LogsUtil.info("Refreshing the page");
        driver.navigate().refresh();
    }

    //close browser
    @Step("Closing the browser")
    public void closeBrowser() {
        LogsUtil.info("Closing the browser");
        if (driver != null) {
            driver.quit(); // 🚀 Ensure this is called
        }
    }
}
