package com.SwagLab.utils.CDP;

import io.qameta.allure.Step;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v138.network.Network;
import org.openqa.selenium.devtools.v138.network.model.ConnectionType;

import java.util.Optional;

public class NetworkProfiles {
    private final DevTools devTools;

    public NetworkProfiles(DevTools devTools) {
        this.devTools = devTools;
        // Enable network domain
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
    }

    // WiFi Simulation
    @Step("WiFi Simulation")
    public void emulateWifi() {
        devTools.send(Network.emulateNetworkConditions(
                false,          // not offline
                20,             // latency (ms)
                5_000_000,      // download (bytes/sec ~ 5 Mbps)
                3_000_000,      // upload (bytes/sec ~ 3 Mbps)
                Optional.of(ConnectionType.WIFI),
                Optional.of(0), Optional.empty(), Optional.empty()));
    }

    // 4G Simulation
    @Step("4G Simulation")
    public void emulate4G() {
        devTools.send(Network.emulateNetworkConditions(
                false,
                50,             // latency
                12_000_000,     // ~12 Mbps
                6_000_000,      // ~6 Mbps
                Optional.of(ConnectionType.CELLULAR4G),
                Optional.of(0), Optional.empty(), Optional.empty()));
    }

    // 3G Simulation
    @Step("3G Simulation")
    public void emulate3G() {
        devTools.send(Network.emulateNetworkConditions(
                false,
                300,            // high latency
                750_000,        // ~750 Kbps
                250_000,        // ~250 Kbps
                Optional.of(ConnectionType.CELLULAR3G),
                Optional.of(0), Optional.empty(), Optional.empty()));
    }

    // Offline Mode
    @Step("Offline Mode Simulation")
    public void goOffline() {
        devTools.send(Network.emulateNetworkConditions(
                true,           // offline
                0,
                0,
                0,
                Optional.of(ConnectionType.NONE),
                Optional.of(0), Optional.empty(), Optional.empty()));
    }

    // Reset back to full speed (no throttling)
    @Step("Reset back to full speed (no throttling)>>ETHERNET")
    public void goOnlineWithFullSpeed() {
        devTools.send(Network.emulateNetworkConditions(
                false,
                0,
                -1,     // -1 means "unlimited"
                -1,
                Optional.of(ConnectionType.ETHERNET),
                Optional.of(0), Optional.empty(), Optional.empty()));
    }
}
