package com.pay.service.money;

import com.pay.vo.SpreadHistoryResponseVO;
import com.pay.vo.SpreadRequestVO;

public interface MoneyService {

    void spread(SpreadRequestVO spreadRequestVO, Long userId, String roomId, String token);

    int getReceiveMoney(Long userId, String roomId, String token);

    SpreadHistoryResponseVO getSpreadHistory(Long userId, String roomId, String token);
}
