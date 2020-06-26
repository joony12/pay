package com.pay.application;

import com.pay.domain.HeaderRequestVO;
import com.pay.domain.money.MoneyService;
import com.pay.domain.money.SpreadMoney;
import com.pay.domain.money.vo.SpreadMoneyRequestVO;
import com.pay.domain.token.TokenService;
import com.pay.exception.TokenCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class MoneyApplicationImpl implements MoneyApplication {

    private final TokenService tokenService;
    private final MoneyService moneyService;

    @Override
    public String spread(HeaderRequestVO headerRequestVO, SpreadMoneyRequestVO spreadMoneyRequestVO) {
        String userId = headerRequestVO.getUserId();
        String token = tokenService.getToken(userId);

        if (StringUtils.isEmpty(token)) {
            throw new TokenCreateException();
        }

        moneyService.createSpreadMoney(headerRequestVO, spreadMoneyRequestVO, token);
        return token;
    }

    @Override
    public Long receive(HeaderRequestVO headerRequestVO, String token) {
        return moneyService.getReceiveMoney(headerRequestVO, token);
    }

    @Override
    public Page<SpreadMoney> spreadList(HeaderRequestVO headerRequestVO, String token, Pageable pageable) {
        return moneyService.spreadList(headerRequestVO, token, pageable);
    }
}