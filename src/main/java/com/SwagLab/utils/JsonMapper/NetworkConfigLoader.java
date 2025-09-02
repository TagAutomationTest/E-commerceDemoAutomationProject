package com.SwagLab.utils.JsonMapper;

import com.SwagLab.utils.Pojos.NetworkProfileConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;

public class NetworkConfigLoader {

    private static Map<String, NetworkProfileConfig> profiles;
    private static final String CONFIG_PATH = "src/main/resources/networkProfiles.json";

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            profiles = mapper.readValue(
                    new File(CONFIG_PATH),
                    mapper.getTypeFactory().constructMapType(Map.class, String.class, NetworkProfileConfig.class)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load networkProfiles.json", e);
        }
    }

    public static NetworkProfileConfig getProfile(String name) {
        return profiles.get(name);
    }
}
