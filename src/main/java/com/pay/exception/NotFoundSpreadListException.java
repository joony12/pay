package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundSpreadListException extends RuntimeException {

    public NotFoundSpreadListException(String message) {
        super(message);
    }

    public NotFoundSpreadListException(String message, Throwable cause) {
        super(message, cause);
    }
}