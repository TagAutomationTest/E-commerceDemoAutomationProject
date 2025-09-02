package com.SwagLab.utils.CDP;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v138.emulation.Emulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CPD_MockGeolocationUtlity {

    private final ChromeDriver driver;
    private final DevTools devTools;

    public CPD_MockGeolocationUtlity(DevTools devTools, ChromeDriver driver) {
        this.devTools = devTools;
        this.driver = driver;
    }


    /* -------------------------
     * GEOLOCATION METHODS
     * ------------------------- */

    /**
     * Mock geo-location using driver.executeCdpCommand
     */
    public void mockGeoLocationCdpCommand(double latitude, double longitude, int accuracy) {
        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("latitude", latitude);
        coordinates.put("longitude", longitude);
        coordinates.put("accuracy", accuracy);

        driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
    }

    /**
     * Mock geo-location using DevTools.send
     */
    public void mockGeoLocationDevTools(double latitude, double longitude, int accuracy) {
        devTools.send(Emulation.setGeolocationOverride(
                Optional.of(latitude),
                Optional.of(longitude),
                Optional.of(accuracy),
                Optional.empty(),  // altitude
                Optional.empty(),  // altitudeAccuracy
                Optional.empty(),  // heading
                Optional.empty()   // speed
        ));
    }
}
