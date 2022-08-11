package com.prototype.weatherservice;

import com.prototype.weatherservice.service.location.Country;
import com.prototype.weatherservice.service.location.impl.CityImpl;
import com.prototype.weatherservice.service.location.impl.CountryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherServiceApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void testExpectedExceptionOnAlreadyRegisteredCity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Country country = new CountryImpl("Country");
            CityImpl city = new CityImpl("City", 0.0F, 0.0F, country);
            country.register(city);
            country.register(city);
        }, "IllegalArgumentException was expected");
    }

}
