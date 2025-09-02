package com.SwagLab.utils.CDP;

import com.SwagLab.utils.JsonMapper.NetworkConfigLoader;
import com.SwagLab.utils.Pojos.NetworkProfileConfig;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v138.network.Network;
import org.openqa.selenium.devtools.v138.network.model.ConnectionType;

import java.util.Optional;

public class NetworkProfiles {
    private final DevTools devTools;
    NetworkProfileConfig profile;

    public NetworkProfiles(DevTools devTools) {
        this.devTools = devTools;
        // Enable network domain
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
    }

    public void emulateNetworkConditions(String profileName) {
        profile = NetworkConfigLoader.getProfile(profileName);

        devTools.send(Network.emulateNetworkConditions(
                !profile.isOnline(),
                profile.getLatency(),
                (int) profile.getDownload(),
                (int) profile.getUpload(),
                Optional.of(ConnectionType.valueOf(profile.getType())),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
    }
}
