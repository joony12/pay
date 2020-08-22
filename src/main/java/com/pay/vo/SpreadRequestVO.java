package com.pay.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SpreadRequestVO {

    private int money;

    private int userCount;

    @Builder
    public SpreadRequestVO(int money, int userCount) {
        this.money = money;
        this.userCount = userCount;
    }
}
