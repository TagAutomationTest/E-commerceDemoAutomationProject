package com.SwagLab.tests.UI;

import com.SwagLab.utils.CDP.ApiMockingUtility;
import com.SwagLab.utils.CDP.CPD_MockGeolocationUtlity;
import com.SwagLab.utils.CDP.ConsoleUtils;
import com.SwagLab.utils.CDP.NetworkProfiles;
import com.SwagLab.utils.chromeEmulatorsUtlity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class relativeLocationg {

    ChromeDriver driver;
    WebDriverWait wait;

    By passwordField;
    By AssignLeave;
    By forgetPassword;
    By userNameLabel;
    By login_Btn;
    By userName_Input;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @Test
    public void loginToOrangeHrmlive() {
        passwordField = By.name("password");
        forgetPassword = By.xpath("//p[(text()='Forgot your password? ')]");
        userNameLabel = By.xpath("//label[text()='Username']");
        AssignLeave = By.xpath("//button[@title='Assign Leave']");

        // ✅ Chaining relative locators (Chaining means the element must satisfy ALL conditions = AND logic)
        userName_Input = RelativeLocator.with(By.tagName("input"))
                .above(passwordField)      // condition 1
                .near(userNameLabel);     // condition 2

        login_Btn = RelativeLocator.with(By.tagName("button"))
                .below(passwordField)      // condition 1
                .above(forgetPassword);   // condition 2

//LOGIN PROCEDURE
        driver.get("https://opensource-demo.orangehrmlive.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(userName_Input)).sendKeys("Admin");
        driver.findElement(passwordField).sendKeys("admin123");
        driver.findElement(login_Btn).click();
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(AssignLeave)).isDisplayed(), "Login Failed");
    }}
