package com.SwagLab.tests.UI;

import com.SwagLab.Drivers.DriverManager;
import com.SwagLab.Listners.TestNGListeners;
import com.SwagLab.Pages.SignInPage;
import com.SwagLab.utils.*;
import io.qameta.allure.Step;
import org.testng.annotations.*;

@Listeners({TestNGListeners.class})
public class LoginTest {
    JsonUtils testData;

    //Tests
    @Step("successfulLogin")
    @Test(priority = 1)
    public void successfulLogin() {
        new SignInPage(DriverManager.getDriver()).enterUserName(testData.getJsonData("login-credentials.username"))
                .enterPassword(testData.getJsonData("login-credentials.password"))
                .clickSubmit()
                .assertSuccessfulLogin(PropertiesUtils.getPropertyValue("homeHeader"));

    }

    @Test(priority = 2)
    public void unSuccessfulLoginWithWrongCredentials() {
        new SignInPage(DriverManager.getDriver())
                .enterUserName(testData.getJsonData("login-credentials.wrong_user"))
                .enterPassword(testData.getJsonData("login-credentials.wrong_password"))
                .clickSubmit()
                .assertUnSuccessfulLogin(PropertiesUtils.getPropertyValue("errorMSG1"));

    }

    @Test(priority = 3)
    public void unSuccessfulLoginWithEmptyCredentials() {
        new SignInPage(DriverManager.getDriver()).clickSubmit()
                .assertUnSuccessfulLogin(PropertiesUtils.getPropertyValue("errorMSG2"));

    }

    @Test(priority = 4)
    public void unSuccessfulLoginWithEmptyPassword() {
        new SignInPage(DriverManager.getDriver())
                .enterUserName(testData.getJsonData("login-credentials.username"))
                .clickSubmit()
                .assertUnSuccessfulLogin(PropertiesUtils.getPropertyValue("errorMSG3"));
    }


    //Configurations
    @BeforeClass
    public void loadLoginTestData() {
        testData = new JsonUtils("test-data");
        LogsUtil.info("Login test data loaded from Json");
    }

    @BeforeMethod
    public void Setup() {
        try {
            DriverManager.createInstance(PropertiesUtils.getPropertyValue("browserType"));
            new SignInPage(DriverManager.getDriver()).navigateToLoginUrl(PropertiesUtils.getPropertyValue("baseURL"));
        } catch (Exception e) {
            throw e;
        }
    }

    @AfterMethod
    public void teardown() {
        LogsUtil.info("Closing the browser");
        if (DriverManager.getDriver() != null) {
            BrowserActions.closeBrowser(DriverManager.getDriver());
        }
    }

}
