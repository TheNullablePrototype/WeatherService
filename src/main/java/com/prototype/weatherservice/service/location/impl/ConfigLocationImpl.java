package com.prototype.weatherservice.service.location.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prototype.weatherservice.service.location.ConfigLocation;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class ConfigLocationImpl implements ConfigLocation {

    float lat, lon;
    @JsonProperty("name")
    String city;
    String country;

}
