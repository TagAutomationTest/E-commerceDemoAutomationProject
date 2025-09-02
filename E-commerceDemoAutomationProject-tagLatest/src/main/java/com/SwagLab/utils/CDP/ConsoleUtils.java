package com.SwagLab.utils.CDP;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v138.log.Log;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUtils {


    private final DevTools devTools;
    private final List<ConsoleLogEntry> capturedLogs = new ArrayList<>();

    public ConsoleUtils(DevTools devTools) {
        this.devTools = devTools;
    }

    /**
     * Enable capturing console logs
     */
    public void enableConsoleLogs() {
        devTools.send(Log.enable());

        devTools.addListener(Log.entryAdded(), logEntry -> {
            ConsoleLogEntry entry = new ConsoleLogEntry(
                    logEntry.getLevel().toString(),
                    logEntry.getText(),
                    logEntry.getUrl().orElse("N/A")
            );
            capturedLogs.add(entry);

            // Also print immediately (optional)
            System.out.println(entry);
        });
    }

    /**
     * Get all captured logs
     */
    public List<ConsoleLogEntry> getCapturedLogs() {
        return new ArrayList<>(capturedLogs); // defensive copy
    }

    /**
     * Filter captured logs by level (INFO, WARNING, ERROR, etc.)
     */
    public List<ConsoleLogEntry> getLogsByLevel(String level) {
        return capturedLogs.stream()
                .filter(entry -> entry.getLevel().equalsIgnoreCase(level))
                .toList();
    }

    /**
     * Clear captured logs
     */
    public void clearLogs() {
        capturedLogs.clear();
    }

    /**
     * Internal log entry wrapper
     */
    public static class ConsoleLogEntry {
        private final String level;
        private final String message;
        private final String url;

        public ConsoleLogEntry(String level, String message, String url) {
            this.level = level;
            this.message = message;
            this.url = url;
        }

        public String getLevel() {
            return level;
        }

        public String getMessage() {
            return message;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "----------\n" +
                    "Level: " + level + "\n" +
                    "Message: " + message + "\n" +
                    "URL: " + url + "\n";
        }
    }
}
