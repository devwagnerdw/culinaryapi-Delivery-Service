package com.culinaryapi.Delivery.Service.exception;


public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}