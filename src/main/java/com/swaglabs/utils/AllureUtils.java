package com.swaglabs.utils;

import io.qameta.allure.Allure;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureUtils {
    public static final String ALLURE_RESULTS_PATH = "test-outputs/allure-results";
    static String REPORT_PATH = "test-outputs/allure-report";
    static String USER_HOME = System.getProperty("user.home");
    static String ALLURE_PATH = USER_HOME + File.separator + ".m2" + File.separator + "repository"
            + File.separator + "allure" + File.separator + "allure-2.33.0" + File.separator
            + "bin" + File.separator + "allure";

    private AllureUtils() {
        super();
    }


    public static void generateAllureReport() {
        //allure, generate, //path ,-o ,//path ,--single-file
        if (PropertiesUtils.getPropertyValue("os.name").toLowerCase().contains("win")) //windows 11
        {
            String WIN = ALLURE_PATH + ".bat";
            TerminalUtils.executeCommand(WIN, "generate", ALLURE_RESULTS_PATH, "-o", REPORT_PATH, "clean", "--single-file");
            LogsUtil.info("Allure report generated successfully on Windows");
        } else {
            TerminalUtils.executeCommand(ALLURE_PATH, "generate", ALLURE_RESULTS_PATH, "-o", REPORT_PATH, "clean", "--single-file");
            LogsUtil.info("Allure report generated successfully on " + PropertiesUtils.getPropertyValue("os.name"));
        }


    }

    public static String renameReport() {
        File newName = new File("Report_" + TimestampUtils.getTimestamp() + ".html");
        File oldName = new File(REPORT_PATH + File.separator + "index.html");
        FilesUtils.renameFile(oldName, newName);
        return newName.getName();
    }

    public static void openReport(String fileName) {
        //allure serve //path
        String reportPath = REPORT_PATH + File.separator + fileName;
        if (PropertiesUtils.getPropertyValue("openAllureAutomatically").equalsIgnoreCase("true")) {
            if (PropertiesUtils.getPropertyValue("os.name").toLowerCase().contains("win")) //windows 11
            {
                TerminalUtils.executeCommand("cmd.exe", "/c", "start", reportPath);
            } else //linux
            {
                TerminalUtils.executeCommand("open", reportPath);
            }
        }
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



