package com.pay.domain.money;

import com.pay.domain.HeaderRequestVO;
import com.pay.domain.money.vo.SpreadMoneyRequestVO;
import com.pay.domain.room.Room;
import com.pay.infra.db.ReceiveMoneyCrudRepository;
import com.pay.infra.db.RoomCrudRepository;
import com.pay.infra.db.SpreadMoneyCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoneyServiceImpl implements MoneyService {

    private final RoomCrudRepository roomCrudRepository;
    private final ReceiveMoneyCrudRepository receiveMoneyCrudRepository;
    private final SpreadMoneyCrudRepository spreadMoneyCrudRepository;

    @Override
    @Transactional
    public void createSpreadMoney(HeaderRequestVO headerRequestVO, SpreadMoneyRequestVO spreadMoneyRequestVO, String token) {

        String roomId = headerRequestVO.getRoomId();
        List<Room> rooms = roomCrudRepository.findRoomByRoomId(roomId);
        List<ReceiveMoney> receiveMonies = createReceiveMoneyList(headerRequestVO, spreadMoneyRequestVO);

        SpreadMoney spreadMoney = SpreadMoney.builder()
                .room(rooms.get(0))
                .spreadAmountMoney(spreadMoneyRequestVO.getSpreadMoney())
                .spreadUserCount(spreadMoneyRequestVO.getSpreadUserCount())
                .token(token)
                .spreadUserId(headerRequestVO.getUserId())
                .receiveMonies(receiveMonies)
                .build();

        spreadMoneyCrudRepository.save(spreadMoney);
    }

    private List<ReceiveMoney> createReceiveMoneyList(HeaderRequestVO headerRequestVO, SpreadMoneyRequestVO spreadMoneyRequestVO) {

        long spreadMoney = spreadMoneyRequestVO.getSpreadMoney();

        List<ReceiveMoney> receiveMonies = new ArrayList<>();
        long spreadUserCount = spreadMoneyRequestVO.getSpreadUserCount();

        for (int i = 0 ; i < spreadUserCount; i ++) {

            long money = spreadMoney / spreadUserCount;

            if (i == spreadUserCount - 1) {
                money += spreadMoney % spreadUserCount;
            }

            ReceiveMoney receiveMoney = ReceiveMoney.builder()
                    .receiveMoney(money)
                    .build();
            receiveMonies.add(receiveMoney);
        }

        receiveMoneyCrudRepository.saveAll(receiveMonies);
        return receiveMonies;
    }
}
