package com.prototype.weatherservice.service.http;

import org.springframework.web.reactive.function.client.WebClient;

public interface WebFluxService {

    WebClient getWebClient();

}
