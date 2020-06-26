package com.pay.application;

import com.pay.domain.HeaderRequestVO;
import com.pay.domain.money.vo.SpreadMoneyRequestVO;

public interface MoneyApplication {
    String spread(HeaderRequestVO headerRequestVO, SpreadMoneyRequestVO spreadMoneyRequestVO);
}
