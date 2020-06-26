package com.pay.domain.money;

import com.pay.domain.HeaderRequestVO;
import com.pay.domain.money.vo.SpreadMoneyRequestVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MoneyService {
    void createSpreadMoney(HeaderRequestVO headerRequestVO, SpreadMoneyRequestVO spreadMoneyRequestVO, String token);

    Long getReceiveMoney(HeaderRequestVO headerRequestVO, String token);

    Page<SpreadMoney> spreadList(HeaderRequestVO headerRequestVO, String token, Pageable pageable);
}
