package com.prototype.weatherservice.controller;

import com.prototype.weatherservice.helper.GsonHelper;
import com.prototype.weatherservice.model.WeatherData;
import com.prototype.weatherservice.service.location.City;
import com.prototype.weatherservice.service.location.LocationService;
import com.prototype.weatherservice.service.weather.WeatherService;
import com.prototype.weatherservice.utils.gson.GsonAdapters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class APIController {

    private final WeatherService weatherService;
    private final LocationService locationService;
    private final GsonHelper gsonHelper;

    @Autowired
    public APIController(WeatherService weatherService,
                         LocationService locationService,
                         GsonHelper gsonHelper) {
        this.weatherService = weatherService;
        this.locationService = locationService;
        this.gsonHelper = gsonHelper;
    }

    @RequestMapping("/run")
    public ResponseEntity<?> runScheduler() {
        this.weatherService.collect();
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/api/weather/v1/")
    public ResponseEntity<?> getWeather(@RequestParam(value = "city") String cityName,
                                        @RequestParam(value = "country", required = false) String countryName,
                                        @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                        LocalDate date) {

        Optional<City> city;

        if (countryName != null) {
            city = locationService.getCityByNameAndCountry(cityName, countryName);
        } else {
            city = locationService.getCityByName(cityName);
        }

        if (city.isPresent()) {

            if (date != null) {
                List<WeatherData> list = weatherService.getWeatherDataByCityAndDate(city.get(), date);

                if (list.isEmpty())
                    return ResponseEntity.notFound().build();

                return ResponseEntity.ok(gsonHelper.getGson().toJson(list, GsonAdapters.TYPE_LIST_CONFIG_LOCATION));
            } else {
                WeatherData data = weatherService.getWeatherDataByCity(city.get());

                if (data == null)
                    return ResponseEntity.notFound().build();

                return ResponseEntity.ok(gsonHelper.getGson().toJson(data));
            }

        }

        return ResponseEntity.notFound().build();
    }

}
