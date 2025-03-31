package com.swaglabs.utils;

import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

public class CustomSoftAssertion extends SoftAssert {


    public static CustomSoftAssertion softAssertion = new CustomSoftAssertion();


    public static void customAssertAll(ITestResult result) {
        try {
            softAssertion.assertAll("Custom Soft Assertion");
        } catch (AssertionError e) {
            LogsUtil.error("Custom Soft Assertion Failed: " + e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        } finally {
            reInitializeSoftAssert();
        }
    }

    private static void reInitializeSoftAssert() {
        softAssertion = new CustomSoftAssertion();
    }

}
