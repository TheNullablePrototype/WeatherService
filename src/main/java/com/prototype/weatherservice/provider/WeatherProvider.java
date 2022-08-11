package com.prototype.weatherservice.provider;

import com.prototype.weatherservice.service.http.WebFluxService;
import com.prototype.weatherservice.service.location.City;

public interface WeatherProvider {

    int getTemperature(WebFluxService service, City city);

}
