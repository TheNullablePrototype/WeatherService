package com.prototype.weatherservice.utils.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.prototype.weatherservice.model.WeatherData;
import com.prototype.weatherservice.provider.aeris.AerisWeather;
import com.prototype.weatherservice.provider.ninjas.NinjasWeather;
import com.prototype.weatherservice.provider.yandex.YandexWeather;
import com.prototype.weatherservice.service.location.ConfigLocation;
import com.prototype.weatherservice.service.location.impl.ConfigLocationImpl;

import java.lang.reflect.Type;
import java.util.List;

public final class GsonAdapters {

    public final static Type TYPE_LIST_CONFIG_LOCATION = new TypeToken<List<ConfigLocation>>() {
    }.getType();

    private GsonAdapters() {
    }

    public static class WeatherDataGsonAdapter implements JsonSerializer<WeatherData> {

        @Override
        public JsonElement serialize(WeatherData src, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("name", src.getCity());
            object.addProperty("country", src.getCountry());
            object.addProperty("temp", src.getTemperature());
            object.addProperty("timestamp", src.getTimestamp().getTime());
            return object;
        }
    }

    public static class YandexWeatherGsonAdapter implements JsonDeserializer<YandexWeather> {

        @Override
        public YandexWeather deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = element.getAsJsonObject();
            if (object.has("fact")) {
                JsonObject fact = object.getAsJsonObject("fact");
                if (fact.has("temp")) {
                    return new YandexWeather(fact.get("temp").getAsInt());
                }
            }
            throw new IllegalStateException();
        }

    }

    public static class AerisWeatherGsonAdapter implements JsonDeserializer<AerisWeather> {

        @Override
        public AerisWeather deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = element.getAsJsonObject();
            if (object.has("success") && object.get("success").getAsBoolean()) {
                JsonObject response = object.getAsJsonObject("response");
                JsonObject ob = response.getAsJsonObject("ob");
                return new AerisWeather(ob.get("tempC").getAsInt());

            }
            throw new IllegalStateException();
        }

    }

    public static class NinjasWeatherGsonAdapter implements JsonDeserializer<NinjasWeather> {

        @Override
        public NinjasWeather deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = element.getAsJsonObject();
            if (object.has("temp"))
                return new NinjasWeather(object.get("temp").getAsInt());
            throw new IllegalStateException();
        }

    }

    public static class ConfigLocationGsonAdapter implements JsonDeserializer<ConfigLocation> {

        @Override
        public ConfigLocation deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = element.getAsJsonObject();
            float lat = object.get("lat").getAsFloat();
            float lon = object.get("lon").getAsFloat();

            if (!(-90.0F <= lat) || !(lat <= 90.0F))
                throw new IllegalArgumentException("Valid latitudes are from -90 to +90 degrees");


            if (!(-180.0F <= lon) || !(lon <= 180.0F)) {
                throw new IllegalArgumentException("Valid longitudes are from -180 to 180 degrees.");
            }

            String city = object.get("name").getAsString();
            String country = object.get("country").getAsString();
            return new ConfigLocationImpl(lat, lon, city, country);
        }

    }


}
