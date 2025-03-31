package com.swaglabs.tests;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.listeners.TestNGListeners;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.utils.JsonUtils;
import com.swaglabs.utils.PropertiesUtils;
import org.testng.annotations.*;

@Listeners(TestNGListeners.class)
public class UserFlowTC {
    //Variables
    GUIDriver driver;
    JsonUtils testData;

    @Test
    public void userFlow() {
        //code
        new LoginPage(driver).enterUsername(testData.getJsonData("login-credentials.username"))
                .enterPassword(testData.getJsonData("login-credentials.password"))
                .clickLoginButton()
                .assertSuccessfulLogin()
                .addSpecificProductToCart(testData.getJsonData("product-names.item1.name"))
                .assertProductAddedToCart(testData.getJsonData("product-names.item1.name"))
                .clickCartIcon()
                .assertProductDetails(testData.getJsonData("product-names.item1.name"), testData.getJsonData("product-names.item1.price"))
                .clickCheckoutButton()
                .fillInformationForm(testData.getJsonData("information-form.firstName"), testData.getJsonData("information-form.lastName"), testData.getJsonData("information-form.postalCode"))
                .assertInformationPage(testData.getJsonData("information-form.firstName"), testData.getJsonData("information-form.lastName"), testData.getJsonData("information-form.postalCode"))
                .clickContinueButton()
                .clickFinishButton()
                .assertConfirmationMessage(testData.getJsonData("confirmation-message"));
    }

    //Configurations
    @BeforeClass
    public void beforeClass() {
        testData = new JsonUtils("test-data");
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        //code
        String browserName = PropertiesUtils.getPropertyValue("browserType");
        driver = new GUIDriver(browserName);
        new LoginPage(driver).navigateToLoginPage();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        //code
        driver.browser().closeBrowser();
    }

}
