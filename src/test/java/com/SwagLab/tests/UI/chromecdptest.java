package com.SwagLab.tests.UI;

import com.SwagLab.utils.CDP.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v138.fetch.model.HeaderEntry;
import org.openqa.selenium.devtools.v138.log.Log;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;

public class chromecdptest {
    ChromeDriver driver;
    DevTools devTools;
    WebDriverWait wait;
    Select select;
    NetworkProfiles profiles;
    ApiMockingUtility apiMock;
    List<HeaderEntry> headers;
    chromeEmulatorMobileDeviceManager chromeEmulator;
    CPD_MockGeolocationUtlity mockGeolocation;
    ConsoleUtils consoleUtils;

    By dropDownLink = By.xpath("//a[text()='Dropdown']");
    By DropDownList = By.id("dropdown");
    By virtualLiberaryBtn = By.xpath("//button[contains(text(),' Library ')]");
    By oneBook_Msg = By.xpath("//p[contains(text(),'Oops')]");
    By locationDiv = By.xpath("//div[text()='Your Location']/following-sibling::div[@class='datavalue']");

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        devTools = driver.getDevTools();
        devTools.createSession();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        profiles = new NetworkProfiles(devTools);
        apiMock = new ApiMockingUtility(devTools);
        chromeEmulator = new chromeEmulatorMobileDeviceManager(devTools);
        mockGeolocation = new CPD_MockGeolocationUtlity(devTools, driver);
        consoleUtils = new ConsoleUtils(devTools);
    }

    /**
     * Logs and views the chrome console logs
     */
    @Test
    public void viewBrowserConsoleLogs() {

        // Enable The Console Logs
        devTools.send(Log.enable());

        // Add A Listener For The Logs
        devTools.addListener(Log.entryAdded(), logEntry -> {
            System.out.println("----------");
            System.out.println("Level: " + logEntry.getLevel());
            System.out.println("Text: " + logEntry.getText());
            System.out.println("Broken URL: " + logEntry.getUrl());
        });
        // Load The AUT
        driver.get("http://the-internet.herokuapp.com/broken_images");
    }

    /**
     * Mock the chrome Geolocation using CDP
     */
    @Test
    public void MockLocation() {
        driver.get("https://my-location.org");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                driver.findElement(By.xpath("//div[contains(text(),'Your Location')]")));
        Assert.assertTrue(driver.findElement(locationDiv).getText().contains("Egypt"));

        // -------- UAE Location (Dubai) --------
        mockGeolocation.mockGeoLocation("Dubai");
        driver.navigate().refresh();
        Assert.assertTrue(driver.findElement(locationDiv).getText().contains("United Arab Emirates"));

        // -------- KSA Location (Riyadh) --------
        mockGeolocation.mockGeoLocation("Riyadh");
        driver.navigate().refresh();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                driver.findElement(By.xpath("//div[contains(text(),'Your Location')]")));
        Assert.assertTrue(driver.findElement(locationDiv).getText().contains("Saudi Arabia"));
    }

    @Test
    public void emulateNetworkProfilesOfflineToOnline() {
        try {
            profiles.emulateNetworkConditions("Offline");
            driver.get("https://the-internet.herokuapp.com/");
        } catch (Exception e) {
            System.out.println("Expected offline failure: " + e.getMessage());
        }
        // Switch to Ethernet (unlimited)
        profiles.emulateNetworkConditions("Ethernet");
        driver.navigate().refresh();
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(dropDownLink)).click();
        WebElement DropList = wait.until(
                ExpectedConditions.visibilityOfElementLocated(DropDownList));
        select = new Select(DropList);
        select.selectByVisibleText("Option 2");
    }

    @Test
    public void emulateChromeDevice() {
        chromeEmulator.emulateDevices("iPhoneSE");
        driver.get("https://the-internet.herokuapp.com/");
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(dropDownLink)).click();
        WebElement DropList = wait.until(
                ExpectedConditions.visibilityOfElementLocated(DropDownList));
        select = new Select(DropList);
        select.selectByVisibleText("Option 2");
    }

    @Test
    public void mockAPI_Url() {
        apiMock.mockUrl("GetBook", "AuthorName=shetty", "AuthorName=BadGuy");
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(virtualLiberaryBtn)).click();
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(oneBook_Msg));
        Assert.assertTrue(driver.findElement(oneBook_Msg).isDisplayed());
    }

    @Test
    public void mockAPI_Response() {
        headers = apiMock.createHeaders(
                Map.of("Content-Type", "application/json",
                        "Access-Control-Allow-Origin", "*",
                        "Cache-Control", "no-store"));

        apiMock.mockResponse("*GetBook.php*", "GetBook", 200, headers, "mockBook");
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(virtualLiberaryBtn)).click();
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(oneBook_Msg));
        Assert.assertTrue(driver.findElement(oneBook_Msg).isDisplayed());

    }

    @Test
    public void simulateApiInternalServerError() {
        apiMock.mockStatusCode(
                "GetBook",
                500,
                "{ \"error\": \"Internal Server Error\" }"
        );
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(virtualLiberaryBtn)).click();
    }

}




