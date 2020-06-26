package com.pay.controller;

import com.pay.application.MoneyApplication;
import com.pay.domain.HeaderRequestVO;
import com.pay.domain.money.vo.SpreadMoneyRequestVO;
import lombok.RequiredArgsConstructor;
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
}