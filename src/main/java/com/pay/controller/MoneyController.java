package com.pay.controller;

import com.pay.application.MoneyApplication;
import com.pay.domain.HeaderRequestVO;
import com.pay.domain.money.SpreadMoney;
import com.pay.domain.money.vo.SpreadMoneyRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping(path = "/money/v1")
@RequiredArgsConstructor
public class MoneyController {

    private final MoneyApplication moneyApplication;

    @PostMapping("/spread")
    public String spread(@SessionAttribute(name = "headerRequestVO") HeaderRequestVO headerRequestVO,
                         @RequestBody SpreadMoneyRequestVO spreadMoneyRequestVO) {

        return moneyApplication.spread(headerRequestVO, spreadMoneyRequestVO);
    }

    @GetMapping("/receive/tokens/{token}")
    public Long receive(@SessionAttribute(name = "headerRequestVO") HeaderRequestVO headerRequestVO,
                        @PathVariable(value = "token") String token) {
        return moneyApplication.receive(headerRequestVO, token);
    }

    @GetMapping("/spread/tokens/{token}")
    public Page<SpreadMoney> spreadList(@SessionAttribute(name = "headerRequestVO") HeaderRequestVO headerRequestVO,
                                        @PathVariable(value = "token") String token,
                                        @PageableDefault(size = 20, sort = "spread_start_time", direction = Sort.Direction.DESC) Pageable pageable) {
        return moneyApplication.spreadList(headerRequestVO, token, pageable);
    }
}