package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SelfReceivedMoneyException extends RuntimeException {

    public SelfReceivedMoneyException(String message) {
        super(message);
    }
}