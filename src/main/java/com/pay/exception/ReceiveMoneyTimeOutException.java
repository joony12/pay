package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReceiveMoneyTimeOutException extends RuntimeException {

    public ReceiveMoneyTimeOutException(String message) {
        super(message);
    }

    public ReceiveMoneyTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}