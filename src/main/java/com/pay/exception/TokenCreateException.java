package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenCreateException extends RuntimeException {

    public TokenCreateException(String message) {
        super(message);
    }

    public TokenCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}