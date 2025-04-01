package com.swaglabs.listeners;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.utils.*;
import org.testng.*;

import java.io.File;

import static com.swaglabs.utils.PropertiesUtils.loadProperties;

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
        FilesUtils.createDirectory(allure_results);
        FilesUtils.createDirectory(logs);
        FilesUtils.createDirectory(screenshots);
    }


    @Override
    public void onExecutionFinish() {
        LogsUtil.info("Test Execution finished");
        AllureUtils.generateAllureReport();
        String reportName = AllureUtils.renameReport();
        AllureUtils.openReport(reportName);
    }


    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            CustomSoftAssertion.customAssertAll(testResult);
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS ->
                        ScreenshotsUtils.takeScreenshot(GUIDriver.getInstance(), "passed-" + testResult.getName());
                case ITestResult.FAILURE ->
                        ScreenshotsUtils.takeScreenshot(GUIDriver.getInstance(), "failed-" + testResult.getName());
                case ITestResult.SKIP ->
                        ScreenshotsUtils.takeScreenshot(GUIDriver.getInstance(), "skipped-" + testResult.getName());
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

