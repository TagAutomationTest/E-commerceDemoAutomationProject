package com.SwagLab.utils.JsonMapper;

import com.SwagLab.utils.Pojos.GeoLocationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;

public class GeoLocationConfigLoader {
    private static final String CONFIG_PATH = "src/main/resources/geoLocations.json";

    public static GeoLocationConfig getLocation(String city) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, GeoLocationConfig> locations =
                    mapper.readValue(new File(CONFIG_PATH),
                            mapper.getTypeFactory().constructMapType(Map.class, String.class, GeoLocationConfig.class));
            return locations.get(city);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load geo location for: " + city, e);
        }
    }
}
