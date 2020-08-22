package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpreadHistorySearchException extends RuntimeException {

    public SpreadHistorySearchException(String message) {
        super(message);
    }
}