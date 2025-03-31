package com.swaglabs.utils;

public class TerminalUtils {
    //allure, generate, //path ,-o ,//path ,--single-file
    //allure generate "+ //path + " -o "+ //path +" --single-file
    public static void executeCommand(String... command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.inheritIO(); // Redirect input/output to the current process
            Process process = processBuilder.start();
            process.waitFor();
            LogsUtil.info("Command executed successfully: " + String.join(" ", command));
        } catch (Exception e) {
            LogsUtil.error("Failed to execute command: " + e.getMessage());
        }
    }
}
