package com.pay.controller;

import com.pay.service.money.MoneyService;
import com.pay.service.token.TokenService;
import com.pay.util.header.HeaderCode;
import com.pay.vo.SpreadHistoryResponseVO;
import com.pay.vo.SpreadRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/money/v1")
@RequiredArgsConstructor
public class MoneyController {

    private final MoneyService moneyService;
    private final TokenService tokenService;

    @PostMapping("/spread")
    public String spread(@RequestHeader(value = HeaderCode.X_USER_ID) Long userId,
                         @RequestHeader(value = HeaderCode.X_ROOM_ID) String roomId,
                         @RequestBody SpreadRequestVO spreadRequestVO) {

        String token = tokenService.getToken(userId);
        moneyService.spread(spreadRequestVO, userId, roomId, token);
        return token;
    }

    @PutMapping("/receive/tokens/{token}")
    public int receive(@RequestHeader(value = HeaderCode.X_USER_ID) Long userId,
                       @RequestHeader(value = HeaderCode.X_ROOM_ID) String roomId,
                       @PathVariable(value = "token") String token) {

        return moneyService.getReceiveMoney(userId, roomId, token);
    }

    @GetMapping("/spread/tokens/{token}")
    public SpreadHistoryResponseVO getSpreadHistory(@RequestHeader(value = HeaderCode.X_USER_ID) Long userId,
                                                    @RequestHeader(value = HeaderCode.X_ROOM_ID) String roomId,
                                                    @PathVariable(value = "token") String token) {

        return moneyService.getSpreadHistory(userId, token);
    }
}