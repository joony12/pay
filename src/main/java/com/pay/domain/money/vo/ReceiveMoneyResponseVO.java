package com.pay.domain.money.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveMoneyResponseVO {
    private String receiveUserId;

    private Long receiveMoney;
}