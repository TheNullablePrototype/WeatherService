package com.prototype.weatherservice.service.http;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;

import java.util.concurrent.Future;

public interface HttpService {

    void initialize();

    void destroy();

    Future<SimpleHttpResponse> createFutureResponse(SimpleHttpRequest request);

}
