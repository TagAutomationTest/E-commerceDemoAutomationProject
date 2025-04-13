package com.SwagLab.tests.UI;

import com.SwagLab.Pages.HomePage;


import com.SwagLab.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;


public class hompageTests {

    private HomePage homePage;
    JsonUtils testData;

    @Test
    void VerifyThatUserIsLoggedInPropely() throws InterruptedException {
        System.out.println("TestcaseHelper --> Testcase 1");
        //  homePage.assertSuccessfulLogin("Products");
        Assert.fail();
    }

    @Test(dependsOnMethods = {"VerifyThatUserIsLoggedInPropely"})
    void testAddSpecificItemToCart() {
        System.out.println("TestcaseHelper --> Testcase 2");
        homePage.addSpecificItemToCart(testData.getJsonData("product-names.item1.name"));
    }


}
