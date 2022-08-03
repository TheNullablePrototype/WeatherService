package com.prototype.weatherservice.service.location.impl;

import com.prototype.weatherservice.service.location.City;
import com.prototype.weatherservice.service.location.Country;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CityImpl implements City {

    private String name;
    private float lat, lon;
    private Country country;

}
