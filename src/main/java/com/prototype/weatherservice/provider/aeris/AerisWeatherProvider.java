package com.prototype.weatherservice.provider.aeris;

import com.prototype.weatherservice.provider.WeatherProvider;
import com.prototype.weatherservice.service.http.WebFluxService;
import com.prototype.weatherservice.service.location.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class AerisWeatherProvider implements WeatherProvider {

    @Value("${weather.aeris.url}")
    private String url;

    @Value("${weather.aeris.api-key}")
    private String token;

    @Value("${weather.aeris.host}")
    private String host;

    @Override
    public int getTemperature(WebFluxService service, City city) {
        return service.getWebClient().get()
                .uri(URI.create(String.format("%s%s,%s", url, city.getLat(), city.getLon())))
                .headers(headers -> {
                    headers.add("X-RapidAPI-Key", token);
                    headers.add("X-RapidAPI-Host", host);
                })
                .retrieve()
                .bodyToFlux(AerisWeather.class)
                .blockFirst()
                .getTemperature();

    }

}
