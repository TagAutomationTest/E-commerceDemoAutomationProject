package com.SwagLab.utils;

import io.qameta.allure.Step;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

public class CustomSoftAssertion extends SoftAssert {


    public static CustomSoftAssertion softAssertion = new CustomSoftAssertion();


    public static void customAssertAll() {
        try {
            softAssertion.assertAll("Custom Soft Assertion");
        } catch (Exception e) {
            System.out.println("Custom Sof Assertion Failed");
        }
    }


}