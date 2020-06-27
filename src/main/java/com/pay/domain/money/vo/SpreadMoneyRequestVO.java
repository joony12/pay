package com.pay.domain.money.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SpreadMoneyRequestVO {

    private Long spreadAmountMoney;

    private Long spreadUserCount;

    @Builder
    public SpreadMoneyRequestVO(Long spreadAmountMoney, Long spreadUserCount) {
        this.spreadAmountMoney = spreadAmountMoney;
        this.spreadUserCount = spreadUserCount;
    }
}
