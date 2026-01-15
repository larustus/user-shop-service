package com.terrasystem.user_shop_service.Config;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TooManyRequestsException extends ResponseStatusException {
    public TooManyRequestsException(String message) {
        super(HttpStatus.TOO_MANY_REQUESTS, message);
    }
}