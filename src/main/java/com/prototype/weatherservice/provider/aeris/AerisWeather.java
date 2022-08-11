package com.prototype.weatherservice.provider.aeris;

import com.prototype.weatherservice.provider.Weather;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class AerisWeather implements Weather {

    Response response;

    @Override
    public int getTemperature() {
        return this.response.ob.tempC;
    }

    @Jacksonized
    @Builder
    @Value
    public static class Response {
        Ob ob;
    }

    @Jacksonized
    @Builder
    @Value
    public static class Ob {
        int tempC;
    }

}
