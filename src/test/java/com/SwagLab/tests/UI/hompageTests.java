package com.SwagLab.tests.UI;

import com.SwagLab.Drivers.DriverManager;
import com.SwagLab.Pages.HomePage;


import com.SwagLab.utils.JsonUtils;
import com.SwagLab.utils.PropertiesUtils;
import org.testng.annotations.Test;


public class hompageTests {

    private HomePage homePage;
    JsonUtils testData;


    @Test(dependsOnMethods = {"VerifyThatUserIsLoggedInPropely"})
    void testAddSpecificItemToCart() {
        System.out.println("TestcaseHelper --> Testcase 2");
        new HomePage(DriverManager.getDriver())
                .navigateToHomePageUrl(PropertiesUtils.getPropertyValue("homeURL"))
                .addSpecificItemToCart(testData.getJsonData("product-names.item1.name"))
                .assertThatProductAddedToCart(testData.getJsonData("product-names.item1.name"));
    }


}
