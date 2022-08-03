package com.prototype.weatherservice.repository;

import com.prototype.weatherservice.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    WeatherData findFirstByCountryAndCityOrderByTimestampDesc(@NonNull String country, @NonNull String city);

    WeatherData findFirstByCityAndTimestamp(@NonNull String city, Timestamp timestamp);

    List<WeatherData> findAllByCountryAndCityAndTimestampBetween(String country, String city, Timestamp timestampStart, Timestamp timestampEnd);

    //WeatherData findByCityAndTimestampBefore(String city, Timestamp timestamp);





    //WeatherData getWeatherDataByCity(String cityName, Timestamp timestamp);

}
