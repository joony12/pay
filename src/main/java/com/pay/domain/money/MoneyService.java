package com.pay.domain.money;

import com.pay.domain.HeaderRequestVO;
import com.pay.domain.money.vo.SpreadMoneyRequestVO;

public interface MoneyService {
    void createSpreadMoney(HeaderRequestVO headerRequestVO, SpreadMoneyRequestVO spreadMoneyRequestVO, String token);

    Long getReceiveMoney(HeaderRequestVO headerRequestVO, String token);
}
