package com.SwagLab.utils;

import io.qameta.allure.Allure;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureUtils {
    static String USER_HOME = System.getProperty("user.home");
    static String ALLURE_PATH = USER_HOME + File.separator + ".m2" + File.separator + "repository"
            + File.separator + "allure" + File.separator + "allure-2.33.0" + File.separator
            + "bin" + File.separator + "allure";

    private AllureUtils() {
        super();
    }

    public static void attacheLogsToAllureReport() {
        try {
            File logFile = FilesUtils.getLatestFile(LogsUtil.LOGS_PATH);
            if (!logFile.exists()) {
                LogsUtil.warn("Log file does not exist: " + LogsUtil.LOGS_PATH);
                return;
            }
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
            LogsUtil.info("Logs attached to Allure report");
        } catch (Exception e) {
            LogsUtil.error("Failed to attach logs to Allure report: " + e.getMessage());
        }
    }

    public static void attachScreenshotToAllure(String screenshotName, String screenshotPath) {
        try {
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenshotPath)));
        } catch (Exception e) {
            LogsUtil.error("Failed to attach screenshot to Allure report: " + e.getMessage());
        }
    }
}
