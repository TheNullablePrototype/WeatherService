package com.prototype.weatherservice.provider.yandex;

import com.prototype.weatherservice.provider.WeatherProvider;
import com.prototype.weatherservice.service.http.WebFluxService;
import com.prototype.weatherservice.service.location.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class YandexWeatherProvider implements WeatherProvider {

    @Value("${weather.yandex.url}")
    private String url;

    @Value("${weather.yandex.api-key}")
    private String token;

    @Override
    public int getTemperature(WebFluxService service, City city) {
//        SimpleHttpRequest request = SimpleRequestBuilder
//                .get("https://api.weather.yandex.ru/v2/forecast?")
//                .addHeader("X-Yandex-API-Key", token)
//                .addParameter("lat", String.valueOf(city.getLat()))
//                .addParameter("lon", String.valueOf(city.getLon()))
//                .addParameter("hours", "false")
//                .addParameter("extra", "false")
//                .build();
//
//        SimpleHttpResponse response = service.createFutureResponse(request).get();
//        Weather weather = this.gsonHelper.getGson().fromJson(response.getBodyText(), YandexWeather.class);
//        return weather.getTemperature();

        return service.getWebClient().get()
                .uri(url, builder -> builder
                        .queryParam("lat", String.valueOf(city.getLat()))
                        .queryParam("lon", String.valueOf(city.getLon()))
                        .queryParam("hours", "false")
                        .queryParam("extra", "false")
                        .build())
                .headers(headers -> headers.add("X-Yandex-API-Key", token))
                .retrieve()
                .bodyToFlux(YandexWeather.class)
                .blockFirst()
                .getTemperature();
    }

}
