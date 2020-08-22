package com.pay.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpreadHistoryResponseVO {

    private String spreadDt;

    private int spreadMoney;

    private int receivedMoney;

    private List<ReceivedHistoryResponseVO> historyResponseVOList;

}