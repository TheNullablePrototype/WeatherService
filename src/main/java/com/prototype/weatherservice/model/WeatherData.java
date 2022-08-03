package com.prototype.weatherservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Getter
@NoArgsConstructor
@ToString
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private int temperature;

    @Column
    @CreationTimestamp
    private Timestamp timestamp;

    public WeatherData(String country, String city, int temperature) {
        this.country = country;
        this.city = city;
        this.temperature = temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherData that = (WeatherData) o;

        if (id != that.id) return false;
        if (temperature != that.temperature) return false;
        if (!city.equals(that.city)) return false;
        if (!country.equals(that.country)) return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + city.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + temperature;
        result = 31 * result + timestamp.hashCode();
        return result;
    }

}
