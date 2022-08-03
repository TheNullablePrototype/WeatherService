package com.prototype.weatherservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prototype.weatherservice.service.location.ConfigLocation;
import com.prototype.weatherservice.service.location.Country;
import com.prototype.weatherservice.service.location.impl.CityImpl;
import com.prototype.weatherservice.service.location.impl.CountryImpl;
import com.prototype.weatherservice.utils.gson.GsonAdapters;
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

    @Test
    void testExceptedExceptionOnBoundariesBeyondValueLatAndLon() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ConfigLocation.class, new GsonAdapters.ConfigLocationGsonAdapter())
                .setPrettyPrinting()
                .create();

        String jsonLat = "{\n" +
                "\t\"name\": \"Saint-Petersburg\",\n" +
                "\t\"country\": \"Russia\",\n" +
                "\t\"lat\": -90.939099,\n" +
                "\t\"lon\": 30.315877\n" +
                "}";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gson.fromJson(jsonLat, ConfigLocation.class);
        });

        String jsonLon = "{\n" +
                "\t\"name\": \"Saint-Petersburg\",\n" +
                "\t\"country\": \"Russia\",\n" +
                "\t\"lat\": -83.939099,\n" +
                "\t\"lon\": 185.315877\n" +
                "}";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gson.fromJson(jsonLon, ConfigLocation.class);
        });

    }

}
