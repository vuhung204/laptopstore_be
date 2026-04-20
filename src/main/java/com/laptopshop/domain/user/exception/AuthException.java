package com.laptopshop.domain.user.exception;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
