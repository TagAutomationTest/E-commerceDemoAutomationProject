package com.SwagLab.tests.UI;

import com.SwagLab.Drivers.DriverManager;
import com.SwagLab.Listners.TestNGListeners;
import com.SwagLab.Pages.*;
import com.SwagLab.utils.BrowserActions;
import com.SwagLab.utils.JsonUtils;
import com.SwagLab.utils.LogsUtil;
import com.SwagLab.utils.PropertiesUtils;
import io.qameta.allure.Step;
import org.testng.annotations.*;

import static com.SwagLab.utils.TimestampUtils.getTimestamp;

@Listeners({TestNGListeners.class})
public class E2eTest {

    JsonUtils testData;
    String F_name;
    String L_name;

    @Step("successful Login")
    @Test()
    public void successfulLogin() {
        new SignInPage(DriverManager.getDriver())
                .enterUserName(testData.getJsonData("login-credentials.username"))
                .enterPassword(testData.getJsonData("login-credentials.password"))
                .clickSubmit()
                .assertSuccessfulLogin(PropertiesUtils.getPropertyValue("homeHeader"));

    }

    @Step("add Item to cart ")
    @Test(dependsOnMethods = "successfulLogin")
    void addingItemToCart() {
        new HomePage(DriverManager.getDriver())
                .navigateToHomePageUrl(PropertiesUtils.getPropertyValue("homeURL"))
                .addSpecificItemToCart(testData.getJsonData("product-names.item1.name"))
                .assertThatProductAddedToCart(testData.getJsonData("product-names.item1.name"));
    }

    @Step("Checkout the added to cart product ")
    @Test(dependsOnMethods = "addingItemToCart")
    void checkoutTheProduct() {
        new HomePage(DriverManager.getDriver())
                .clickOnCartIcon()
                .assertProductDetails(
                        testData.getJsonData("product-names.item1.name"),
                        testData.getJsonData("product-names.item1.price"))
                .checkoutItem();
    }

    @Step("fill Checkout Information ")
    @Test(dependsOnMethods = "checkoutTheProduct")
    void fillCheckoutInformation() {
        new CheckoutInformationPage(DriverManager.getDriver())
                .fillCheckoutInformation(F_name, L_name, testData.getJsonData("information-form.postalCode"))
                .assertOnFilledData(F_name, L_name, testData.getJsonData("information-form.postalCode"))
                .clickOnContinue();
    }

    @Step("fill Checkout Information ")
    @Test(dependsOnMethods = "fillCheckoutInformation")
    void finishCheckoutAndPay() {
        new CheckoutOverviewPage(DriverManager.getDriver())
                .CliclOnFinishButton()
                .assertOnConfirmationMessage(testData.getJsonData("confirmation-message"));
    }

    @BeforeClass
    public void loadLoginTestData() {
        testData = new JsonUtils("test-data");
        F_name = testData.getJsonData("information-form.firstName") + getTimestamp();
        L_name = testData.getJsonData("information-form.lastName") + getTimestamp();
        LogsUtil.info("Login test data loaded from Json");
        DriverManager.createInstance(PropertiesUtils.getPropertyValue("browserType"));
        new SignInPage(DriverManager.getDriver()).navigateToLoginUrl(PropertiesUtils.getPropertyValue("baseURL"));
    }

    @AfterClass
    public void teardown() {
        LogsUtil.info("Closing the browser");
        if (DriverManager.getDriver() != null) {
            BrowserActions.closeBrowser(DriverManager.getDriver());
        }
    }
}
