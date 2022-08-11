package com.prototype.weatherservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prototype.weatherservice.utils.CustomTimestampSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonIgnore
    private Long id;

    @Column
    private String country;

    @JsonProperty("name")
    @Column
    private String city;

    @JsonProperty("temp")
    @Column
    private int temperature;

    @Column
    @CreationTimestamp
    @JsonSerialize(using = CustomTimestampSerializer.class)
    private Timestamp timestamp;

    public WeatherData(String country, String city, int temperature) {
        this.country = country;
        this.city = city;
        this.temperature = temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof WeatherData))
            return false;

        WeatherData other = (WeatherData) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
