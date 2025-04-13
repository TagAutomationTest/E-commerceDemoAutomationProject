package com.SwagLab.tests.UI;

import com.SwagLab.Drivers.DriverManager;
import com.SwagLab.Listners.TestNGListeners;
import com.SwagLab.Pages.CheckoutOverviewPage;
import com.SwagLab.Pages.SignInPage;
import com.SwagLab.utils.BrowserActions;
import com.SwagLab.utils.JsonUtils;
import com.SwagLab.utils.LogsUtil;
import com.SwagLab.utils.PropertiesUtils;
import io.qameta.allure.Step;
import org.testng.annotations.*;

@Listeners({TestNGListeners.class})
public class UserFlowTest {


    JsonUtils testData;

    @Step("Login-->Add product to cart --> Checkout product -->fill Checkout Information -->pay product")
    @Test()
    void userFlow1() {
        new SignInPage(DriverManager.getDriver())
                .enterUserName(testData.getJsonData("login-credentials.username"))
                .enterPassword(testData.getJsonData("login-credentials.password"))
                .clickSubmit()
                .assertSuccessfulLogin(PropertiesUtils.getPropertyValue("homeHeader"))
                .navigateToHomePageUrl(PropertiesUtils.getPropertyValue("homeURL"))
                .addSpecificItemToCart(testData.getJsonData("product-names.item1.name"))
                .assertThatProductAddedToCart(testData.getJsonData("product-names.item1.name"))
                .clickOnCartIcon()
                .assertProductDetails(
                        testData.getJsonData("product-names.item1.name"),
                        testData.getJsonData("product-names.item1.price"))
                .checkoutItem()
                .fillCheckoutInformation(
                        testData.getJsonData("information-form.firstName"),
                        testData.getJsonData("information-form.lastName"),
                        testData.getJsonData("information-form.postalCode"))
                .assertOnFilledData(
                        testData.getJsonData("information-form.firstName"),
                        testData.getJsonData("information-form.lastName"),
                        testData.getJsonData("information-form.postalCode"))
                .clickOnContinue()
                .CliclOnFinishButton()
                .assertOnConfirmationMessage(testData.getJsonData("confirmation-message"));


    }


    //Configurations

    @BeforeClass
    public void setup() {
        testData = new JsonUtils("test-data");
    }

    @BeforeMethod
    public void loadLoginTestData() {

        LogsUtil.info("Login test data loaded from Json");
        DriverManager.createInstance(PropertiesUtils.getPropertyValue("browserType"));
        new SignInPage(DriverManager.getDriver()).navigateToLoginUrl(PropertiesUtils.getPropertyValue("baseURL"));
    }

    @AfterMethod
    public void teardown() {
        LogsUtil.info("Closing the browser");
        if (DriverManager.getDriver() != null) {
            BrowserActions.closeBrowser(DriverManager.getDriver());
        }
    }

}
