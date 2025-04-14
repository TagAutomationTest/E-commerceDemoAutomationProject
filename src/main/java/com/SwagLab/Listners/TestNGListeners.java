package com.SwagLab.Listners;

import com.SwagLab.Drivers.DriverManager;
import com.SwagLab.utils.*;
import org.testng.*;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.SwagLab.utils.PropertiesUtils.loadProperties;

public class TestNGListeners implements IExecutionListener, ITestListener, IInvokedMethodListener {

    File allure_results = new File("test-outputs/allure-results");
    File logs = new File("test-outputs/Logs");
    File screenshots = new File("test-outputs/screenshots");
    private static final Map<String, Throwable> configFailures = new ConcurrentHashMap<>();


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
        LogsUtil.info("Generating and serving Allure report...");
        AllureUtils.opnAllureReportAfterExecution();
    }


    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod()) {
            LogsUtil.info("⚙️ Preparing configuration for ", method.getTestMethod().getMethodName());
        }
        if (method.isTestMethod()) {
            LogsUtil.info("🚀 Starting test: ", testResult.getName());
        }
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
                        ScreenshotsUtils.takeScreenshot(DriverManager.getDriver(), "✅ PASSED -" + testResult.getName());
                case ITestResult.FAILURE ->
                        ScreenshotsUtils.takeScreenshot(DriverManager.getDriver(), "❌ FAILED - " + testResult.getName());
                case ITestResult.SKIP ->
                        ScreenshotsUtils.takeScreenshot(DriverManager.getDriver(), "⏭️ SKIPPED - " + testResult.getName());
            }
            AllureUtils.attacheLogsToAllureReport();
        }
        if (method.isConfigurationMethod() && testResult.getStatus() == ITestResult.FAILURE) {
            String classKey = "CLASS:" + method.getTestMethod().getTestClass().getName();
            configFailures.put(classKey, testResult.getThrowable());
        }


    }

    @Override
    public void onTestStart(ITestResult result) {
        // Check for class-level config failures
        if (configFailures.containsKey("CLASS:" + result.getTestClass().getName())) {
            result.setStatus(ITestResult.SKIP);
            result.setThrowable(configFailures.get("CLASS:" + result.getTestClass().getName()));
            throw new SkipException("Skipped due to @BeforeClass failure");
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
        LogsUtil.info(" test case : ", result.getName(), "⏭️ Skipped", "With failure" + result.getThrowable().getMessage());
    }


}