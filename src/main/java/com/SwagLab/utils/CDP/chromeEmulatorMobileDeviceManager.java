package com.SwagLab.utils.CDP;


import com.SwagLab.utils.JsonMapper.MobileDeviceConfigLoader;
import com.SwagLab.utils.Pojos.MobileDeviceConfig;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v138.emulation.Emulation;
import java.util.Optional;

public class chromeEmulatorMobileDeviceManager {
    MobileDeviceConfig config;
    private final DevTools devTools;

    public chromeEmulatorMobileDeviceManager(DevTools devTools) {
        this.devTools = devTools;
    }

    public void emulateDevices(String deviceName) {
        config = MobileDeviceConfigLoader.getDevice(deviceName);
        devTools.send(Emulation.setDeviceMetricsOverride(
                config.getWidth(),
                config.getHeight(),
                config.getDeviceScaleFactor(),
                config.isMobile(),
                Optional.empty(),
                Optional.of(config.getWidth()),
                Optional.of(config.getHeight()),
                Optional.of(0),
                Optional.of(0),
                Optional.of(config.isMobile()),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
        devTools.send(Emulation.setUserAgentOverride(
                config.getUserAgent(),
                Optional.of("en-US"),
                Optional.of(config.getPlatform()),
                Optional.empty()
        ));
    }
}


