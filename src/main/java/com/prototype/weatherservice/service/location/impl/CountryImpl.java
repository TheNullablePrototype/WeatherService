package com.prototype.weatherservice.service.location.impl;

import com.prototype.weatherservice.service.location.City;
import com.prototype.weatherservice.service.location.Country;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class CountryImpl implements Country {

    private String name;

    @EqualsAndHashCode.Exclude
    private Map<String, City> cities = new HashMap<>();

    public CountryImpl(String name) {
        this.name = name;
    }

    @Override
    public void register(City city) {
        if (this.cities.containsKey(city.getName()))
            throw new IllegalArgumentException("City '" + city.getName() + "' already registered in country '" + getName() + "'");
        this.cities.put(city.getName(), city);
    }

    @Override
    public City getCityByName(String cityName) {
        return this.cities.get(cityName);
    }

}
