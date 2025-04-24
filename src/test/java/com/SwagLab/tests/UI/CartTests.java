package com.SwagLab.tests.UI;

import com.SwagLab.Drivers.DriverManager;
import com.SwagLab.Pages.HomePage;
import com.SwagLab.utils.JsonUtils;
import org.testng.annotations.Test;

public class CartTests {

    JsonUtils testData;

    @Test(priority = 1)
    void VerifyOpenCart() throws InterruptedException {
        System.out.println("TestcaseHelper --> Testcase 2");
        new HomePage(DriverManager.getDriver())
                .clickOnCartIcon()
                .assertProductDetails(
                        testData.getJsonData("product-names.item1.name"),
                        testData.getJsonData("product-names.item1.price"))
                .checkoutItem();
    }


}
