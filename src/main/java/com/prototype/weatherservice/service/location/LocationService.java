package com.prototype.weatherservice.service.location;

import java.util.Map;
import java.util.Optional;

public interface LocationService {

    void init();

    Optional<City> getCityByName(String name);

    Optional<Country> getCountryByName(String name);

    Optional<City> getCityByNameAndCountry(String cityName, String countryName);

    Map<String, Country> getCountries();


}
