package com.prototype.weatherservice.provider.ninjas;

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
public class NinjasWeatherProvider implements WeatherProvider {

    @Value(value = "${weather.ninjas.url}")
    private String url;

    @Value(value = "${weather.ninjas.api-key}")
    private String token;

    @Value(value = "${weather.ninjas.host}")
    private String host;


    private final GsonHelper gsonHelper;

    @Autowired
    public NinjasWeatherProvider(GsonHelper gsonHelper) {
        this.gsonHelper = gsonHelper;
    }

    @Override
    @SneakyThrows
    public int getTemperature(HttpService httpService, City city) {
        SimpleHttpRequest request = SimpleRequestBuilder
                .get(this.url)
                .addHeader("X-RapidAPI-Key", token)
                .addHeader("X-RapidAPI-Host", host)
                .addParameter("lat", String.valueOf(city.getLat()))
                .addParameter("lon", String.valueOf(city.getLon()))
                .build();

        SimpleHttpResponse response = httpService.createFutureResponse(request).get();
        Weather weather = this.gsonHelper.getGson().fromJson(response.getBodyText(), NinjasWeather.class);
        return weather.getTemperature();
    }

}
