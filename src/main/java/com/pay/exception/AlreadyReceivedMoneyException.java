package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlreadyReceivedMoneyException extends RuntimeException {

    public AlreadyReceivedMoneyException(String message) {
        super(message);
    }

    public AlreadyReceivedMoneyException(String message, Throwable cause) {
        super(message, cause);
    }
}