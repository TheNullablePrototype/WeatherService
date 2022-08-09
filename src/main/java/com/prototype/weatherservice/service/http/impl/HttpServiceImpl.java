package com.prototype.weatherservice.service.http.impl;

import com.prototype.weatherservice.service.http.HttpService;
import com.prototype.weatherservice.utils.web.SimpleFutureCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.Future;

@Service
@Slf4j
public final class HttpServiceImpl implements HttpService {

    private final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
            .setIOReactorConfig(IOReactorConfig.custom()
                    .setIoThreadCount(5)
                    .setSoTimeout(Timeout.ofSeconds(5))
                    .build())
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setConnectTimeout(Timeout.ofSeconds(5))
                    .setConnectionRequestTimeout(Timeout.ofSeconds(5))
                    .setResponseTimeout(Timeout.ofSeconds(5))
                    .build())
            .build();

    @Override
    @PostConstruct
    public void initialize() {
        this.client.start();
    }

    @Override
    public Future<SimpleHttpResponse> createFutureResponse(SimpleHttpRequest request) {
        return this.client.execute(
                SimpleRequestProducer.create(request),
                SimpleResponseConsumer.create(),
                new SimpleFutureCallback(request)
        );
    }

    @Override
    @PreDestroy
    public void destroy() {
        try {
            this.client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
