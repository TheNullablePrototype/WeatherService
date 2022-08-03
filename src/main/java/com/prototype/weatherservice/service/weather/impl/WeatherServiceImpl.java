package com.prototype.weatherservice.service.weather.impl;

import com.prototype.weatherservice.model.WeatherData;
import com.prototype.weatherservice.provider.WeatherProvider;
import com.prototype.weatherservice.repository.WeatherDataRepository;
import com.prototype.weatherservice.service.http.HttpService;
import com.prototype.weatherservice.service.location.City;
import com.prototype.weatherservice.service.location.Country;
import com.prototype.weatherservice.service.location.LocationService;
import com.prototype.weatherservice.service.weather.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final List<WeatherProvider> providers;
    private final LocationService locationService;
    private final HttpService httpService;
    private final WeatherDataRepository repository;

    @Value(value = "${weather.collect.scheduler.enabled}")
    private boolean scheduledEnabled;

    @Autowired
    public WeatherServiceImpl(List<WeatherProvider> providers,
                              LocationService locationService,
                              HttpService httpService,
                              WeatherDataRepository repository) {
        this.providers = providers;
        this.locationService = locationService;
        this.httpService = httpService;
        this.repository = repository;
    }

    @Override
    @Scheduled(fixedRateString = "${weather.collect.interval}", timeUnit = TimeUnit.MINUTES)
    public void collectWeatherData() {

        if (scheduledEnabled) {
            logger.info("Collecting weather data...");

            for (Map.Entry<String, Country> entry : locationService.getCountries().entrySet()) {
                for (Map.Entry<String, City> cityEntry : entry.getValue().getCities().entrySet()) {
                    IntSummaryStatistics statistics = this.providers.stream()
                            .unordered()
                            .parallel()
                            .mapToInt(value -> value.getTemperature(this.httpService, cityEntry.getValue()))
                            .summaryStatistics();

                    WeatherData data = new WeatherData(entry.getKey(), cityEntry.getKey(), (int) Math.round(statistics.getAverage()));
                    this.repository.save(data);
                }
            }

            logger.info("Done.");
        }

    }

    @Override
    public void collect() {
        logger.info("Collecting weather data...");

        for (Map.Entry<String, Country> entry : locationService.getCountries().entrySet()) {
            for (Map.Entry<String, City> cityEntry : entry.getValue().getCities().entrySet()) {
                IntSummaryStatistics statistics = this.providers.stream()
                        .unordered()
                        .parallel()
                        .mapToInt(value -> value.getTemperature(this.httpService, cityEntry.getValue()))
                        .summaryStatistics();
                WeatherData data = new WeatherData(entry.getKey(), cityEntry.getKey(), (int) Math.round(statistics.getAverage()));
                this.repository.save(data);
            }
        }

        logger.info("Done.");
    }

    @Override
    public WeatherData getWeatherDataByCity(City city) {
        return this.repository.findFirstByCountryAndCityOrderByTimestampDesc(city.getCountry().getName(), city.getName());
    }

    @Override
    public List<WeatherData> getWeatherDataByCityAndDate(City city, LocalDate date) {
        final Timestamp start = Timestamp.valueOf(date.atStartOfDay());
        final Timestamp end = Timestamp.valueOf(date.plusDays(1).atStartOfDay());
        return this.repository.findAllByCountryAndCityAndTimestampBetween(city.getCountry().getName(), city.getName(), start, end);
    }

}
