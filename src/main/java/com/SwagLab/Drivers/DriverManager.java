package com.SwagLab.Drivers;

import com.SwagLab.utils.LogsUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.fail;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        super();
    }

    @Step("Create driver instance on {Browser}")
    public static WebDriver createInstance(String Browser) {

        WebDriver driver = BrowserFactory.getBrowser(Browser);
        LogsUtil.info("Driver created on : ", Browser);
        setDriver(driver);

        return getDriver();
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            LogsUtil.error("Driver is null");
            fail("Driver is null");
            return null;
        }
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }
}
