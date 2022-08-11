package com.prototype.weatherservice.provider.yandex;

import com.prototype.weatherservice.provider.Weather;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class YandexWeather implements Weather {

    Fact fact;

    @Override
    public int getTemperature() {
        return this.fact.temp;
    }

    @Jacksonized
    @Builder
    @Value
    public static class Fact {

        int temp;

    }

}
