package com.culinaryapi.Delivery.Service.exception;

public class HttpClientException extends RuntimeException{
    public HttpClientException(String message) {
        super(message);
    }
}