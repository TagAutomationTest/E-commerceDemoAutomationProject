package com.SwagLab.utils.Pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileDeviceConfig {
    private String name;
    private int width;
    private int height;
    private double deviceScaleFactor;
    private boolean mobile;
    private String userAgent;
    private String platform;
}