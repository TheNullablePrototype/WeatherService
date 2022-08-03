package com.prototype.weatherservice.provider;

import lombok.Getter;

@Getter
public abstract class Weather {

    private final int temperature;

    protected Weather(int temperature) {
        this.temperature = temperature;
    }

}
