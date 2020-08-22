package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnRegisteredRoomException extends RuntimeException {

    public UnRegisteredRoomException(String message) {
        super(message);
    }

    public UnRegisteredRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}