package com.SwagLab.utils.Pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class NetworkProfileConfig {

    private boolean online;
    private double latency;
    private double download;
    private double upload;
    private String type; // store as String, map to ConnectionType later

}

