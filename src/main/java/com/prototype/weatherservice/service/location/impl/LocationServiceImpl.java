package com.prototype.weatherservice.service.location.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.prototype.weatherservice.service.location.City;
import com.prototype.weatherservice.service.location.ConfigLocation;
import com.prototype.weatherservice.service.location.Country;
import com.prototype.weatherservice.service.location.LocationService;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@Value
public class LocationServiceImpl implements LocationService {

    Map<String, Country> countries = new HashMap<>();

    ObjectMapper mapper;

    public LocationServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<City> getCityByName(String cityName) {
        for (Map.Entry<String, Country> entry : this.countries.entrySet()) {
            City city = entry.getValue().getCityByName(cityName);
            if (city != null) return Optional.of(city);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Country> getCountryByName(String countryName) {
        return Optional.ofNullable(this.countries.get(countryName));
    }

    @Override
    public Optional<City> getCityByNameAndCountry(String cityName, String countryName) {
        Optional<Country> country = getCountryByName(countryName);
        return country.map(value -> value.getCityByName(cityName));
    }

    @PostConstruct
    @SneakyThrows
    @Override
    public void init() {

        final Path path = Paths.get("cities.json");
        if (Files.exists(path)) {

            final String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            ObjectReader reader = mapper.readerForListOf(ConfigLocationImpl.class);
            List<ConfigLocation> locations = reader.readValue(json);
            if (locations.isEmpty())
                throw new IllegalArgumentException("Locations is empty");
            else
                logger.info("Loaded {} locations", locations.size());

            locations.forEach(this::register);
        }

    }

    private void register(ConfigLocation location) {
        Country country = countries.computeIfAbsent(location.getCountry(), CountryImpl::new);
        City city = new CityImpl(location.getCity(), location.getLat(), location.getLon(), country);
        country.register(city);
        logger.info("Registered city {} in country {}", city.getName(), country.getName());
    }

}
