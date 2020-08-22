package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnRegisteredUserException extends RuntimeException {

    public UnRegisteredUserException(String message) {
        super(message);
    }

    public UnRegisteredUserException(String message, Throwable cause) {
        super(message, cause);
    }
}