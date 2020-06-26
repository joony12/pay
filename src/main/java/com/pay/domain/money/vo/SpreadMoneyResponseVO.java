package com.pay.domain.money.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpreadMoneyResponseVO {

    private String token;

    private Long spreadAmountMoney;

    private LocalDateTime spreadStartTime;

    List<ReceiveMoneyResponseVO> responseVOList;
}
