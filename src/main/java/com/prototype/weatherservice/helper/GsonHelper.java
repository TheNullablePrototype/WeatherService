package com.prototype.weatherservice.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prototype.weatherservice.model.WeatherData;
import com.prototype.weatherservice.provider.aeris.AerisWeather;
import com.prototype.weatherservice.provider.ninjas.NinjasWeather;
import com.prototype.weatherservice.provider.yandex.YandexWeather;
import com.prototype.weatherservice.service.location.ConfigLocation;
import com.prototype.weatherservice.utils.gson.GsonAdapters;
import org.springframework.stereotype.Component;

@Component
public class GsonHelper {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(YandexWeather.class, new GsonAdapters.YandexWeatherGsonAdapter())
            .registerTypeAdapter(AerisWeather.class, new GsonAdapters.AerisWeatherGsonAdapter())
            .registerTypeAdapter(NinjasWeather.class, new GsonAdapters.NinjasWeatherGsonAdapter())
            .registerTypeAdapter(ConfigLocation.class, new GsonAdapters.ConfigLocationGsonAdapter())
            .registerTypeAdapter(WeatherData.class, new GsonAdapters.WeatherDataGsonAdapter())
            .setPrettyPrinting()
            .create();

    public Gson getGson() {
        return this.gson;
    }

}
