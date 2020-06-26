package com.pay.domain.money;

import com.pay.domain.HeaderRequestVO;
import com.pay.domain.money.vo.SpreadMoneyRequestVO;
import com.pay.domain.room.Room;
import com.pay.domain.user.User;
import com.pay.exception.NotFoundSpreadListException;
import com.pay.exception.NotFoundUserException;
import com.pay.exception.NotParticipateRoomException;
import com.pay.exception.ReceiveMoneyTimeOutException;
import com.pay.infra.db.ReceiveMoneyCrudRepository;
import com.pay.infra.db.RoomCrudRepository;
import com.pay.infra.db.SpreadMoneyCrudRepository;
import com.pay.infra.db.UserCrudRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoneyServiceImpl implements MoneyService {

    private final UserCrudRepository userCrudRepository;
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

    @Override
    @Transactional
    public Long getReceiveMoney(HeaderRequestVO headerRequestVO, String token) {
        String userId = headerRequestVO.getUserId();
        String roomId = headerRequestVO.getRoomId();

        SpreadMoney spreadMoney = spreadMoneyCrudRepository.findSpreadMoneyByToken(token).orElse(null);

        User user = userCrudRepository.findUserByUserId(userId).orElse(null);

        if (ObjectUtils.isEmpty(user)) {
            throw new NotFoundUserException();
        }

        boolean validUser = roomCrudRepository.existsByRoomIdAndUser(roomId, user);

        if (!validUser) {
            throw new NotParticipateRoomException();
        }

        if (ObjectUtils.isEmpty(spreadMoney)) {
            return 0L;
        }

        if (spreadMoney.getSpreadUserId().equals(headerRequestVO.getUserId())) {
            return 0L;
        }

        if (spreadMoney.getSpreadStartTime().plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new ReceiveMoneyTimeOutException();
        }

        receiveMoneyCrudRepository.receiveMoney(userId, LocalDateTime.now());

        ReceiveMoney receiveMoney = receiveMoneyCrudRepository.findReceiveMoneyByReceiveUserId(userId).orElse(null);

        if (ObjectUtils.isEmpty(receiveMoney)) {
            return 0L;
        }

        return receiveMoney.getReceiveMoney();
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "spreadMoneyList", key = "#headerRequestVO.userId + '_' + #token + #pageable.pageNumber + '_' + #pageable.pageSize", unless = "#result == null")
    public Page<SpreadMoney> spreadList(HeaderRequestVO headerRequestVO, String token, Pageable pageable) {
        String userId = headerRequestVO.getUserId();

        Page<SpreadMoney> spreadMonies = spreadMoneyCrudRepository.findSpreadMoneyByTokenAndSpreadStartTimeAfter(token, LocalDateTime.now().minusDays(7), pageable);

        for (SpreadMoney spreadMoney : spreadMonies) {
            if (!spreadMoney.getSpreadUserId().equals(userId)) {
                throw new NotFoundSpreadListException();
            }
        }

        return spreadMonies;
    }

    private List<ReceiveMoney> createReceiveMoneyList(HeaderRequestVO headerRequestVO, SpreadMoneyRequestVO spreadMoneyRequestVO) {

        long spreadMoney = spreadMoneyRequestVO.getSpreadMoney();

        List<ReceiveMoney> receiveMonies = new ArrayList<>();
        long spreadUserCount = spreadMoneyRequestVO.getSpreadUserCount();

        for (int i = 0; i < spreadUserCount; i++) {

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
