package com.prototype.weatherservice.provider.aeris;

import com.prototype.weatherservice.helper.GsonHelper;
import com.prototype.weatherservice.provider.Weather;
import com.prototype.weatherservice.provider.WeatherProvider;
import com.prototype.weatherservice.service.http.HttpService;
import com.prototype.weatherservice.service.location.City;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AerisWeatherProvider implements WeatherProvider {

    @Value(value = "${weather.aeris.url}")
    private String url;

    @Value(value = "${weather.aeris.api-key}")
    private String token;

    @Value(value = "${weather.aeris.host}")
    private String host;

    private final GsonHelper gsonHelper;

    @Autowired
    public AerisWeatherProvider(GsonHelper gsonHelper) {
        this.gsonHelper = gsonHelper;
    }

    @Override
    @SneakyThrows
    public int getTemperature(HttpService httpService, City city) {
        SimpleHttpRequest request = SimpleRequestBuilder
                .get(String.format("%s%s,%s", url, city.getLat(), city.getLon()))
                .addHeader("X-RapidAPI-Key", token)
                .addHeader("X-RapidAPI-Host", host)
                .addParameter("lat", String.valueOf(city.getLat()))
                .addParameter("lon", String.valueOf(city.getLon()))
                .build();

        SimpleHttpResponse response = httpService.createFutureResponse(request).get();
        Weather weather = this.gsonHelper.getGson().fromJson(response.getBodyText(), AerisWeather.class);
        return weather.getTemperature();
    }

}
