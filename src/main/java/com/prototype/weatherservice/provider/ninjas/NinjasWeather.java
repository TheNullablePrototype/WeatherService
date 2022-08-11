package com.prototype.weatherservice.provider.ninjas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prototype.weatherservice.provider.Weather;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class NinjasWeather implements Weather {

    @JsonProperty("temp")
    int temperature;

}
