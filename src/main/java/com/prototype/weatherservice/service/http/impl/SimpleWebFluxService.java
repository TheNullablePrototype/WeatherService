package com.prototype.weatherservice.service.http.impl;

import com.prototype.weatherservice.service.http.WebFluxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class SimpleWebFluxService implements WebFluxService {

    private final WebClient webClient;

    @Autowired
    public SimpleWebFluxService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public WebClient getWebClient() {
        return this.webClient;
    }

}
