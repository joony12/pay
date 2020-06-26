package com.pay.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class HeaderRequestVO {

    private boolean valid;

    private String userId;

    private String roomId;

    @Builder
    public HeaderRequestVO(boolean valid, String userId, String roomId) {
        this.valid = valid;
        this.userId = userId;
        this.roomId = roomId;
    }
}