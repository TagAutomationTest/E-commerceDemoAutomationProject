package com.SwagLab.utils.CDP;

import com.SwagLab.utils.JsonMapper.GeoLocationConfigLoader;
import com.SwagLab.utils.Pojos.GeoLocationConfig;
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

    /**
     * Overrides the browser's geolocation using Chrome DevTools Protocol (CDP).
     * <p>
     * This method sets a mock location by providing latitude, longitude, and accuracy values.
     * It is useful for testing location-based features in web applications
     * without needing to physically change the device or system location.
     *
     * @param latitude  the latitude value of the mock location
     * @param longitude the longitude value of the mock location
     * @param accuracy  the accuracy of the mock location in meters
     */
    public void mockGeoLocationCdpCommand(double latitude, double longitude, int accuracy) {
        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("latitude", latitude);
        coordinates.put("longitude", longitude);
        coordinates.put("accuracy", accuracy);

        driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
    }

    /**
     * Overrides the browser's geolocation using Chrome DevTools Protocol (CDP).
     * <p>
     * This method sets a mock location by providing latitude, longitude, and accuracy values.
     * It is useful for testing location-based features in web applications
     * without needing to physically change the device or system location.
     *
     * @param latitude  the latitude value of the mock location
     * @param longitude the longitude value of the mock location
     * @param accuracy  the accuracy of the mock location in meters
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

    public void mockGeoLocation(String city) {
        GeoLocationConfig config = GeoLocationConfigLoader.getLocation(city);

        devTools.send(Emulation.setGeolocationOverride(
                Optional.of(config.getLatitude()),
                Optional.of(config.getLongitude()),
                Optional.of(config.getAccuracy()),
                Optional.empty(),  // altitude
                Optional.empty(),  // altitudeAccuracy
                Optional.empty(),  // heading
                Optional.empty()   // speed

        ));
    }
}
