package com.prototype.weatherservice.utils.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.message.StatusLine;

@Slf4j
public class SimpleFutureCallback implements FutureCallback<SimpleHttpResponse> {

    private final SimpleHttpRequest request;

    public SimpleFutureCallback(SimpleHttpRequest request) {
        this.request = request;
    }

    @Override
    public void completed(SimpleHttpResponse result) {
        logger.info("Completed request {} with code: {}", request, new StatusLine(result));
    }

    @Override
    public void failed(Exception ex) {
        logger.error("Failed request " + request, ex);
    }

    @Override
    public void cancelled() {
        logger.warn("Cancelled request: " + request);
    }

}