package com.SwagLab.utils.JsonMapper;

import com.SwagLab.utils.Pojos.MobileDeviceConfig;
import com.SwagLab.utils.Pojos.NetworkProfileConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MobileDeviceConfigLoader {
    private static Map<String, MobileDeviceConfig> devices;
    private static final String CONFIG_PATH = "src/main/resources/mobileDevices.json";

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            devices = mapper.readValue(
                    new File(CONFIG_PATH),
                    mapper.getTypeFactory().constructMapType(Map.class, String.class, MobileDeviceConfig.class)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load networkProfiles.json", e);
        }
    }

    public static MobileDeviceConfig getDevice(String name) {
        return devices.get(name);
    }
}
