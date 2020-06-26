package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundRoomException extends RuntimeException {

    public NotFoundRoomException(String message) {
        super(message);
    }

    public NotFoundRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}