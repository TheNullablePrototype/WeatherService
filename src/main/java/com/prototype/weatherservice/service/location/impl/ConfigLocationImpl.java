package com.prototype.weatherservice.service.location.impl;

import com.prototype.weatherservice.service.location.ConfigLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConfigLocationImpl implements ConfigLocation {

    private final float lat, lon;
    private final String city, country;

}
