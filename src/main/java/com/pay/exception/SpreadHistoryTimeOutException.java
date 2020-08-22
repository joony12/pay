package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpreadHistoryTimeOutException extends RuntimeException {

    public SpreadHistoryTimeOutException(String message) {
        super(message);
    }

    public SpreadHistoryTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}