package com.prototype.weatherservice.provider.yandex;

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
import org.springframework.stereotype.Component;

@Component
public class YandexWeatherProvider implements WeatherProvider {

    @Value(value = "${weather.yandex.api-key}")
    private String token;

    private final GsonHelper gsonHelper;

    @Autowired
    public YandexWeatherProvider(GsonHelper gsonHelper) {
        this.gsonHelper = gsonHelper;
    }

    @SneakyThrows
    @Override
    public int getTemperature(HttpService httpService, City city) {
        SimpleHttpRequest request = SimpleRequestBuilder
                .get("https://api.weather.yandex.ru/v2/forecast?")
                .addHeader("X-Yandex-API-Key", token)
                .addParameter("lat", String.valueOf(city.getLat()))
                .addParameter("lon", String.valueOf(city.getLon()))
                .addParameter("hours", "false")
                .addParameter("extra", "false")
                .build();

        SimpleHttpResponse response = httpService.createFutureResponse(request).get();
        Weather weather = this.gsonHelper.getGson().fromJson(response.getBodyText(), YandexWeather.class);
        return weather.getTemperature();
    }


}
