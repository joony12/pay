package com.pay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotParticipateRoomException extends RuntimeException {

    public NotParticipateRoomException(String message) {
        super(message);
    }

    public NotParticipateRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}