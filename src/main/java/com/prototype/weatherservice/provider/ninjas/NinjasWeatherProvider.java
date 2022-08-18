package com.prototype.weatherservice.provider.ninjas;

import com.prototype.weatherservice.provider.WeatherProvider;
import com.prototype.weatherservice.service.http.WebFluxService;
import com.prototype.weatherservice.service.location.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NinjasWeatherProvider implements WeatherProvider {

    @Value("${weather.ninjas.url}")
    private String url;

    @Value("${weather.ninjas.api-key}")
    private String token;

    @Value("${weather.ninjas.host}")
    private String host;

    @Override
    public int getTemperature(WebFluxService service, City city) {

        return service.getWebClient().get()
                .uri(url, builder -> {
                    builder.queryParam("lat", String.valueOf(city.getLat()));
                    builder.queryParam("lon", String.valueOf(city.getLon()));
                    return builder.build();
                })
                .headers(headers -> {
                    headers.add("X-RapidAPI-Key", token);
                    headers.add("X-RapidAPI-Host", host);
                })
                .retrieve()
                .bodyToFlux(NinjasWeather.class)
                .blockFirst()
                .getTemperature();
    }

}
