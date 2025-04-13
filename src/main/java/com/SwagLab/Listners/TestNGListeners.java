package com.SwagLab.Listners;

import com.SwagLab.Drivers.DriverManager;
import com.SwagLab.utils.*;
import org.testng.*;

import java.io.File;

import static com.SwagLab.utils.PropertiesUtils.loadProperties;

public class TestNGListeners implements IExecutionListener, ITestListener, IInvokedMethodListener {

    File allure_results = new File("test-outputs/allure-results");
    File logs = new File("test-outputs/Logs");
    File screenshots = new File("test-outputs/screenshots");


    @Override
    public void onExecutionStart() {
        LogsUtil.info("Test Execution started");
        loadProperties();
        FilesUtils.deleteFiles(allure_results);
        FilesUtils.cleanDirectory(logs);
        FilesUtils.cleanDirectory(screenshots);

    }

    @Override
    public void onExecutionFinish() {
        LogsUtil.info("Test Execution finished");
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            try {
                CustomSoftAssertion.customAssertAll();
            } catch (AssertionError e) {
                testResult.setStatus(ITestResult.FAILURE);
                testResult.setThrowable(e);
            }
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS ->
                        ScreenshotsUtils.takeScreenshot(DriverManager.getDriver(), "passed - " + testResult.getName());
                case ITestResult.FAILURE ->
                        ScreenshotsUtils.takeScreenshot(DriverManager.getDriver(), "failed - " + testResult.getName());
                case ITestResult.SKIP ->
                        ScreenshotsUtils.takeScreenshot(DriverManager.getDriver(), "skipped - " + testResult.getName());
            }
            AllureUtils.attacheLogsToAllureReport();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogsUtil.info("Test case", result.getName(), "passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogsUtil.info("Test case", result.getName(), "failed");

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogsUtil.info("Test case", result.getName(), "skipped");

    }

}