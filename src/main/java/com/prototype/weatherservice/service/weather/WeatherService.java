package com.prototype.weatherservice.service.weather;

import com.prototype.weatherservice.model.WeatherData;
import com.prototype.weatherservice.service.location.City;

import java.time.LocalDate;
import java.util.List;

public interface WeatherService {

    void collectWeatherData();

    void collect();

    WeatherData getWeatherDataByCity(City city);

    List<WeatherData> getWeatherDataByCityAndDate(City city, LocalDate date);

}
