package com.prototype.weatherservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-weather-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-weather-data-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class APIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getWeatherDataByCity() throws Exception {

        String expectedContent = "{\"country\":\"Russia\",\"timestamp\":1659520800000,\"name\":\"Moscow\",\"temp\":26}";

        this.mockMvc.perform(get("/api/weather/v1/").param("city", "Moscow"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

    }

    @Test
    void getWeatherDataByCityAndCountry() throws Exception {

        String expectedContent = "{\"country\":\"Russia\",\"timestamp\":1659520800000,\"name\":\"Moscow\",\"temp\":26}";

        this.mockMvc.perform(get("/api/weather/v1/")
                        .param("city", "Moscow")
                        .param("country", "Russia"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

    }

    @Test
    void getWeatherDataByNotExistsCity() throws Exception {
        this.mockMvc.perform(get("/api/weather/v1/")
                        .param("city", "city"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getWeatherDataByCityAndNotExistsCountry()throws Exception  {
        this.mockMvc.perform(get("/api/weather/v1/")
                        .param("city", "Moscow")
                        .param("country", "country"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getWeatherDataByCityAndDate()throws Exception  {

        String expectedContent = "[{\"country\":\"Russia\",\"timestamp\":1659517200000,\"name\":\"Moscow\",\"temp\":27},{\"country\":\"Russia\",\"timestamp\":1659520800000,\"name\":\"Moscow\",\"temp\":26}]";

        this.mockMvc.perform(get("/api/weather/v1/")
                        .param("city", "Moscow")
                        .param("date", "2022-08-03"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

    }

    @Test
    void getWeatherDataByCityAndCountryAndDate() throws Exception {

        String expectedContent = "[{\"country\":\"Russia\",\"timestamp\":1659517200000,\"name\":\"Moscow\",\"temp\":27},{\"country\":\"Russia\",\"timestamp\":1659520800000,\"name\":\"Moscow\",\"temp\":26}]";

        this.mockMvc.perform(get("/api/weather/v1/")
                        .param("city", "Moscow")
                        .param("country", "Russia")
                        .param("date", "2022-08-03"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

    }

    @Test
    void getWeatherDataByCityAndIncorrectDate() throws Exception{
        this.mockMvc.perform(get("/api/weather/v1/")
                        .param("city", "Moscow")
                        .param("date", "data"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getWeatherDataByCityAndCountryAndIncorrectDate() throws Exception {
        this.mockMvc.perform(get("/api/weather/v1/")
                        .param("city", "Moscow")
                        .param("country", "country")
                        .param("date", "DATE"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getWeatherDataByCityAndFutureDate() throws Exception {
        this.mockMvc.perform(get("/api/weather/v1/")
                        .param("city", "Moscow")
                        .param("date", "2022-09-03"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getWeatherDataByCityAndPastDate() throws Exception {
        this.mockMvc.perform(get("/api/weather/v1/")
                        .param("city", "Moscow")
                        .param("date", "2022-07-03"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}