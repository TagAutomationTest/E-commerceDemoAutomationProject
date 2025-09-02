package com.SwagLab.utils.Pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocationConfig {
    private double latitude;
    private double longitude;
    private int accuracy;
}

