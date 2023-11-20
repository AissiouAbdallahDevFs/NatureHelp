package com.NatureHelp.api.Config;

public class JwtValidationException extends RuntimeException {

    public JwtValidationException(String message) {
        super(message);
    }
}
