package com.SwagLab.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AllureUtils {
    private static final String AllureResultDirectory = "test-outputs/allure-results";
    private static final String AllureReportDirectory = "test-outputs/allure-report";
    private static final String PortNumber = PropertiesUtils.getPropertyValue("portNumber");

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

    public static void openAllureAfterExecution() {
        try {
            // First generate the report
            generateAllureReport();

            // Then serve the report if generation was successful
            // serveAllureReport();
            serveAllureReportOnStaticPort();

        } catch (Exception e) {
            LogsUtil.error("Failure during opening Allure report after execution: " + e.getMessage());
        }
    }

    public static void generateAllureReport() {
        try {
            LogsUtil.info("Generating Allure report...");

            ProcessBuilder generateBuilder = new ProcessBuilder("cmd.exe", "/c", "allure generate "
                    + AllureResultDirectory + " --clean -o " + AllureReportDirectory);
            Process generateProcess = generateBuilder.start();
            int generateExitCode = generateProcess.waitFor();

            if (generateExitCode != 0) {
                LogsUtil.error("Failed to generate Allure report. Exit code: " + generateExitCode);
                return;
            }

            LogsUtil.info("Allure report generated successfully.");

        } catch (IOException e) {
            LogsUtil.error("IOException occurred while generating Allure report: " + e.getMessage());
        } catch (Exception e) {
            LogsUtil.error("Unexpected error occurred while generating Allure report: " + e.getMessage());
        }
    }

    public static void serveAllureReport() {
        try {
            LogsUtil.info("Serving Allure report...");
            new ProcessBuilder("cmd.exe", "/c", "allure serve " + AllureResultDirectory).start();
            LogsUtil.info("Allure report is being served.");

        } catch (IOException e) {
            LogsUtil.error("IOException occurred while serving Allure report: " + e.getMessage());
        } catch (Exception e) {
            LogsUtil.error("Unexpected error occurred while serving Allure report: " + e.getMessage());
        }
    }

    // allure open -p 8085 test-outputs/allure-report
    public static void serveAllureReportOnStaticPort() {
        try {
            LogsUtil.info("Serving Allure report...");
            new ProcessBuilder("cmd.exe", "/c", "allure open -p " + PortNumber + " " + AllureReportDirectory).start();
            LogsUtil.info("Allure report is being served.");

        } catch (IOException e) {
            LogsUtil.error("IOException occurred while serving Allure report: " + e.getMessage());
        } catch (Exception e) {
            LogsUtil.error("Unexpected error occurred while serving Allure report: " + e.getMessage());
        }
    }

    public static void killAllureOnlyOnPort(int port) {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // Get PID from netstat
                Process findPid = Runtime.getRuntime().exec("cmd /c netstat -ano | findstr :" + port);
                BufferedReader pidReader = new BufferedReader(new InputStreamReader(findPid.getInputStream()));
                String line;

                while ((line = pidReader.readLine()) != null) {
                    if (line.contains("LISTENING") || line.contains("ESTABLISHED")) {
                        String[] parts = line.trim().split("\\s+");
                        String pid = parts[parts.length - 1];

                        // Get process name by PID
                        Process findProc = Runtime.getRuntime().exec("cmd /c tasklist /FI \"PID eq " + pid + "\"");
                        BufferedReader procReader = new BufferedReader(new InputStreamReader(findProc.getInputStream()));
                        String procLine;
                        while ((procLine = procReader.readLine()) != null) {
                            if (procLine.toLowerCase().contains("java") || procLine.toLowerCase().contains("allure")) {
                                System.out.println("Killing PID " + pid + " running Allure/java");
                                Runtime.getRuntime().exec("cmd /c taskkill /PID " + pid + " /F");
                            }
                        }
                    }
                }

            } else {
                // macOS/Linux version
                String[] findCmd = {"/bin/sh", "-c", "lsof -i :" + port + " | grep LISTEN"};
                Process find = Runtime.getRuntime().exec(findCmd);
                BufferedReader reader = new BufferedReader(new InputStreamReader(find.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.toLowerCase().contains("java") || line.toLowerCase().contains("allure")) {
                        String[] parts = line.trim().split("\\s+");
                        String pid = parts[1]; // usually second column is PID
                        System.out.println("Killing PID " + pid + " running Allure/java");
                        Runtime.getRuntime().exec("kill -9 " + pid);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error trying to kill port " + port);
            e.printStackTrace();
        }
    }
}




