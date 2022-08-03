package com.prototype.weatherservice.service.location;

import java.util.Map;

public interface Country {

    String getName();

    void register(City city);

    City getCityByName(String cityName);

    Map<String, City> getCities();

}
