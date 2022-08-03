package com.prototype.weatherservice.provider;

import com.prototype.weatherservice.service.http.HttpService;
import com.prototype.weatherservice.service.location.City;

public interface WeatherProvider {

    int getTemperature(HttpService httpService, City city);

}
