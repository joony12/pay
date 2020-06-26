package com.pay.application;

import com.pay.domain.HeaderRequestVO;
import com.pay.domain.money.SpreadMoney;
import com.pay.domain.money.vo.SpreadMoneyRequestVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MoneyApplication {
    String spread(HeaderRequestVO headerRequestVO, SpreadMoneyRequestVO spreadMoneyRequestVO);

    Long receive(HeaderRequestVO headerRequestVO, String token);

    Page<SpreadMoney> spreadList(HeaderRequestVO headerRequestVO, String token, Pageable pageable);
}
