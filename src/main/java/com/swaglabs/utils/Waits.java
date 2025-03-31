package com.swaglabs.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.swaglabs.utils.PropertiesUtils.getPropertyValue;

public class Waits {
    private WebDriver driver;

    public Waits(WebDriver driver) {
        this.driver = driver;
    }

    //present - visible - clickable

    //wait for element to be present
    public WebElement waitForElementPresent(By locator) {
        //code
        LogsUtil.info("Waiting for element to be present: ", locator.toString());
        return new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(getPropertyValue("explicitWait")))).
                until(driver1 ->
                        driver1.findElement(locator));
    }

    //wait for element to be visible
    public WebElement waitForElementVisible(By locator) {
        //code
        LogsUtil.info("Waiting for element to be visible: ", locator.toString());
        return new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(getPropertyValue("explicitWait")))).
                until(driver1 ->
                        {
                            WebElement element = waitForElementPresent(locator);
                            return element.isDisplayed() ? element : null;
                        }
                );
    }

    //wait for element to be clickable
    public WebElement waitForElementClickable(By locator) {
        //code
        LogsUtil.info("Waiting for element to be clickable: ", locator.toString());
        return new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(getPropertyValue("explicitWait")))).
                until(driver1 ->
                        {
                            WebElement element = waitForElementVisible(locator);
                            return element.isEnabled() ? element : null;
                        }
                );
    }

    //general wait
    public FluentWait<WebDriver> Synchronize() {
        //code
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(100));
    }


}
