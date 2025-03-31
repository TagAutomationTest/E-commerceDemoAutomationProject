package com.swaglabs.tests;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.listeners.TestNGListeners;
import com.swaglabs.pages.CartPage;
import com.swaglabs.pages.HomePage;
import com.swaglabs.pages.InformationPage;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.utils.JsonUtils;
import com.swaglabs.utils.PropertiesUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.swaglabs.utils.TimestampUtils.getTimestamp;

@Listeners(TestNGListeners.class)
public class E2e {

    //Variables
    GUIDriver driver;
    JsonUtils testData;
    String FIRST_NAME;
    String LAST_NAME;

    //Tests

    @Test
    public void successfulLogin() {
        new LoginPage(driver).enterUsername(testData.getJsonData("login-credentials.username"))
                .enterPassword(testData.getJsonData("login-credentials.password"))
                .clickLoginButton()
                .assertSuccessfulLogin();
    }

    @Test(dependsOnMethods = "successfulLogin")
    public void addingProductToCart() {
        new HomePage(driver).addSpecificProductToCart(testData.getJsonData("product-names.item1.name"))
                .assertProductAddedToCart(testData.getJsonData("product-names.item1.name"));
    }

    @Test(dependsOnMethods = "addingProductToCart")
    public void checkoutProduct() {
        new HomePage(driver).clickCartIcon()
                .assertProductDetails(testData.getJsonData("product-names.item1.name"), testData.getJsonData("product-names.item1.price"));
    }

    @Test(dependsOnMethods = "checkoutProduct")
    public void fillInformationForm() {
        new CartPage(driver).clickCheckoutButton()
                .fillInformationForm(FIRST_NAME, LAST_NAME, testData.getJsonData("information-form.postalCode"))
                .assertInformationPage(FIRST_NAME, LAST_NAME, testData.getJsonData("information-form.postalCode"));
    }

    @Test(dependsOnMethods = "fillInformationForm")
    public void finishCheckout() {
        new InformationPage(driver)
                .clickContinueButton()
                .clickFinishButton()
                .assertConfirmationMessage(testData.getJsonData("confirmation-message"));
    }

    //Configurations
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        //code
        testData = new JsonUtils("test-data");
        FIRST_NAME = testData.getJsonData("information-form.firstName") + getTimestamp();
        LAST_NAME = testData.getJsonData("information-form.lastName") + getTimestamp();
        String browserName = PropertiesUtils.getPropertyValue("browserType");
        driver = new GUIDriver(browserName);
        new LoginPage(driver).navigateToLoginPage();
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() {
        //code
        driver.browser().closeBrowser();
    }

}
