package com.prototype.weatherservice.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient(@Value("${web.client.connection.timeout}") int timeout,
                               @Value("${web.client.connection.response.timeout}") int responseTimeout,
                               @Value("${web.client.connection.read.timeout}") int readTimeout,
                               @Value("${web.client.connection.write.timeout}") int writeTimeout) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, ((int) TimeUnit.SECONDS.toMillis(timeout)))
                .responseTimeout(Duration.ofSeconds(responseTimeout))
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.SECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.SECONDS));
                });

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);

        return WebClient.builder()
                .uriBuilderFactory(factory)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
