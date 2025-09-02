package com.SwagLab.tests.UI;

import com.SwagLab.utils.CDP.ApiMockingUtility;
import com.SwagLab.utils.CDP.CPD_MockGeolocationUtlity;
import com.SwagLab.utils.CDP.ConsoleUtils;
import com.SwagLab.utils.CDP.NetworkProfiles;
import com.SwagLab.utils.chromeEmulatorsUtlity;
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

public class chrome_CDPTest {
    ChromeDriver driver;
    DevTools devTools;
    WebDriverWait wait;
    Select select;
    NetworkProfiles profiles;
    ApiMockingUtility apiMock;
    List<HeaderEntry> headers;
    chromeEmulatorsUtlity chromeEmulator;
    CPD_MockGeolocationUtlity mockGeolocation;
    ConsoleUtils consoleUtils;

    //Locators
    By dropDownLink = By.xpath("//a[text()='Dropdown']");
    By DropDownList = By.id("dropdown");
    By virtualLiberaryBtn = By.xpath("//button[contains(text(),' Library ')]");
    By oneBook_Msg = By.xpath("//p[contains(text(),'Oops')]");
    By locationDiv=By.xpath("//div[text()='Your Location']/following-sibling::div[@class='datavalue']");

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        devTools = driver.getDevTools();
        devTools.createSession();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        profiles = new NetworkProfiles(devTools);
        apiMock = new ApiMockingUtility(devTools);
        chromeEmulator = new chromeEmulatorsUtlity(devTools);
        mockGeolocation = new CPD_MockGeolocationUtlity(devTools, driver);
        consoleUtils = new ConsoleUtils(devTools);
    }

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

    @Test
    public void mockGeoLocation_executeCDPCommand() {
        // -------- UAE Location (Dubai) --------
        mockGeolocation.mockGeoLocationCdpCommand(25.276987, 55.296249, 1);
        driver.get("https://my-location.org/");
    }

    @Test
    public void mockGeoLocation_DevTools() throws InterruptedException {
        //My Real Geolocation (Egypt)
        driver.get("https://my-location.org");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                driver.findElement(By.xpath("//div[contains(text(),'Your Location')]")));
        Assert.assertTrue(driver.findElement(locationDiv).getText().contains("Egypt"));

        // -------- UAE Location (Dubai) --------
        mockGeolocation.mockGeoLocationDevTools(25.276987, 55.296249, 1);
        driver.navigate().refresh();
        Assert.assertTrue(driver.findElement(locationDiv).getText().contains("United Arab Emirates"));

        // -------- KSA Location (Riyadh) --------
        mockGeolocation.mockGeoLocationDevTools(24.7136, 46.6753, 1);
        driver.navigate().refresh();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                driver.findElement(By.xpath("//div[contains(text(),'Your Location')]")));
        Assert.assertTrue(driver.findElement(locationDiv).getText().contains("Saudi Arabia"));
    }

    @Test
    public void simulteSlowConnection3G() {

        profiles.emulate3G();
        driver.get("https://the-internet.herokuapp.com/");
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(dropDownLink)).click();
        WebElement DropList = wait.until(
                ExpectedConditions.visibilityOfElementLocated(DropDownList));
        select = new Select(DropList);
        select.selectByVisibleText("Option 2");

    }

    @Test
    public void switchFromOfflineToOnline() {

        profiles.goOffline();
        try {
            driver.get("https://the-internet.herokuapp.com/");
        } catch (Exception e) {
            System.out.println("Expected offline failure: " + e.getMessage());
        }
        profiles.emulate3G();
        driver.navigate().refresh();
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(dropDownLink)).click();
        WebElement DropList = wait.until(
                ExpectedConditions.visibilityOfElementLocated(DropDownList));
        select = new Select(DropList);
        select.selectByVisibleText("Option 2");

    }

    @Test
    public void emulateMobileEmulatorforPredefindDevices() {
        chromeEmulator.emulateDevice("iPad");
        driver.get("https://the-internet.herokuapp.com/");
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(dropDownLink)).click();
        WebElement DropList = wait.until(
                ExpectedConditions.visibilityOfElementLocated(DropDownList));
        select = new Select(DropList);
        select.selectByVisibleText("Option 2");
    }

    @Test
    public void emulateMobileEmulatorforcustomDevices() {
        chromeEmulator.emulateCustomDevice(
                414, 896, 3,
                "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2 like Mac OS X) AppleWebKit/605.1.15 " +
                        "(KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1",
                "iOS");
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




