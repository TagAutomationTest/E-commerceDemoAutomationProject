package com.SwagLab.utils;


import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v138.emulation.Emulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class chromeEmulatorsUtlity {

    private final DevTools devTools;

    public chromeEmulatorsUtlity(DevTools devTools) {
        this.devTools = devTools;
    }

    /**
     * Predefined device profiles (you can add more as needed)
     */
    private static final Map<String, DeviceProfile> DEVICE_PROFILES = new HashMap<>();

    static {
        DEVICE_PROFILES.put("iPhone6", new DeviceProfile(375, 667, 2,
                "Mozilla/5.0 (iPhone; CPU iPhone OS 13_6 like Mac OS X) " +
                        "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                        "Version/13.1.2 Mobile/15E148 Safari/604.1",
                "iOS"));

        DEVICE_PROFILES.put("iPhoneX", new DeviceProfile(375, 812, 3,
                "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) " +
                        "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                        "Version/14.0 Mobile/15E148 Safari/604.1",
                "iOS"));

        DEVICE_PROFILES.put("iPad", new DeviceProfile(768, 1024, 2,
                "Mozilla/5.0 (iPad; CPU OS 14_0 like Mac OS X) " +
                        "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                        "Version/14.0 Mobile/15E148 Safari/604.1",
                "iOS"));

        DEVICE_PROFILES.put("GalaxyS9", new DeviceProfile(360, 740, 4,
                "Mozilla/5.0 (Linux; Android 9; SM-G960F Build/PPR1.180610.011) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/91.0.4472.77 Mobile Safari/537.36",
                "Android"));
    }

    /**
     * Apply a predefined device emulation
     */
    public void emulateDevice(String deviceName) {
        DeviceProfile profile = DEVICE_PROFILES.get(deviceName);
        if (profile == null) {
            throw new IllegalArgumentException("Device profile not found: " + deviceName);
        }
        applyEmulation(profile);
    }

    /**
     * Apply a custom device emulation
     */
    public void emulateCustomDevice(int width, int height, int deviceScaleFactor,
                                    String userAgent, String platform) {
        DeviceProfile custom = new DeviceProfile(width, height, deviceScaleFactor, userAgent, platform);
        applyEmulation(custom);
    }

    /**
     * Internal method to apply CDP emulation
     */
    private void applyEmulation(DeviceProfile profile) {
        devTools.send(Emulation.setDeviceMetricsOverride(
                profile.width,
                profile.height,
                profile.deviceScaleFactor,
                true,
                Optional.empty(),
                Optional.of(profile.width),
                Optional.of(profile.height),
                Optional.of(0),
                Optional.of(0),
                Optional.of(false),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));

        devTools.send(Emulation.setUserAgentOverride(
                profile.userAgent,
                Optional.of("en-US"),
                Optional.of(profile.platform),
                Optional.empty()
        ));
    }

    /**
     * Device profile holder
     */
    private static class DeviceProfile {
        int width;
        int height;
        int deviceScaleFactor;
        String userAgent;
        String platform;

        DeviceProfile(int width, int height, int deviceScaleFactor,
                      String userAgent, String platform) {
            this.width = width;
            this.height = height;
            this.deviceScaleFactor = deviceScaleFactor;
            this.userAgent = userAgent;
            this.platform = platform;
        }
    }
}


