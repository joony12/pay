package com.pay.service.money;

import com.pay.entity.Money;
import com.pay.entity.Room;
import com.pay.entity.User;
import com.pay.exception.*;
import com.pay.repository.MoneyCrudRepository;
import com.pay.repository.RoomCrudRepository;
import com.pay.repository.UserCrudRepository;
import com.pay.util.type.MoneyTypeEnum;
import com.pay.vo.ReceivedHistoryResponseVO;
import com.pay.vo.SpreadHistoryResponseVO;
import com.pay.vo.SpreadRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoneyServiceImpl implements MoneyService {

    private final UserCrudRepository userCrudRepository;
    private final RoomCrudRepository roomCrudRepository;
    private final MoneyCrudRepository moneyCrudRepository;

    @Override
    @Transactional
    public void spread(SpreadRequestVO spreadRequestVO, Long userId, String roomId, String token) {

        List<Money> monies = new ArrayList<>();

        User user = getUser(userId);
        Room room = getRoom(roomId);

        int money = spreadRequestVO.getMoney();
        int userCount = spreadRequestVO.getUserCount();

        Money spreadMoney = createSpreadMoneyHistory(user, room, money, userCount, token);
        List<Money> receiveMoneys = createReceiveMoneyHistory(user, room, money, userCount, token);

        monies.add(spreadMoney);
        monies.addAll(receiveMoneys);

        moneyCrudRepository.saveAll(monies);
    }

    @Override
    @Transactional
    public int getReceiveMoney(Long userId, String roomId, String token) {

        Money spreadMoney = getSpreadMoney(token);
        checkSpreadMoney(userId, roomId, spreadMoney);

        int spreadUserCount = spreadMoney.getUserCount();
        User user = getUser(userId);
        List<Money> receiveMoneyList = getReceivedMoneyUserNotInList(token, user);
        checkAlreadyReceivedMoney(spreadUserCount, receiveMoneyList.size());

        return calculateReceiveMoney(spreadUserCount, user, receiveMoneyList);
    }

    private Integer calculateReceiveMoney(int spreadUserCount, User user, List<Money> receiveMoneyList) {
        for (int i = 0; i < spreadUserCount; i++) {
            Money money = receiveMoneyList.get(i);

            try {
                money.setUserId(user.getUserId());
                money.setUpdateDt(LocalDateTime.now());
                moneyCrudRepository.save(money);
            } catch (OptimisticLockingFailureException ex) {
                continue;
            }

            return money.getMoney();
        }

        return 0;
    }

    private void checkAlreadyReceivedMoney(int spreadUserCount, int receivedMoneyListSize) {
        if (spreadUserCount != receivedMoneyListSize) {
            throw new AlreadyReceivedMoneyException("뿌리기 당 한 사용자는 한번만 받을 수 있습니다.");
        }
    }

    @Override
    @Transactional
    public SpreadHistoryResponseVO getSpreadHistory(Long userId, String roomId, String token) {
        Money spreadMoney = getSpreadMoney(token);
        checkEnableSearch(spreadMoney.getCreateDt());

        List<Money> receivedMoneyList = getReceivedMoneyList(token);
        int receivedMoney = receivedMoneyList.stream().mapToInt(Money::getMoney).sum();

        return SpreadHistoryResponseVO.builder()
                .spreadDt(spreadMoney.getCreateDt())
                .spreadMoney(spreadMoney.getMoney())
                .receivedMoney(receivedMoney)
                .historyResponseVOList(getHistoryResponseVOList(receivedMoneyList))
                .build();
    }

    private void checkEnableSearch(LocalDateTime createdDt) {
        if (createdDt.plusDays(7).compareTo(LocalDateTime.now()) < 0) {
            throw new SpreadHistoryTimeOutException("뿌린 건에 대한 조회는 7일 동안 할 수 있습니다.");
        }
    }

    private List<ReceivedHistoryResponseVO> getHistoryResponseVOList(List<Money> receivedMoneyList) {
        return receivedMoneyList.stream()
                .map(money -> ReceivedHistoryResponseVO.builder()
                        .money(money.getMoney())
                        .userId(money.getUserId())
                        .build())
                .collect(Collectors.toList());
    }

    private void checkSpreadMoney(Long userId, String roomId, Money spreadMoney) {
        if (spreadMoney.getUserId().equals(userId)) {
            throw new SelfReceivedMoneyException("자신이 뿌리기한 건은 자신이 받을 수 없습니다.");
        }

        if (!spreadMoney.getRoom().getRoomId().equals(roomId)) {
            throw new NotParticipateRoomException("같은 방에 속하지 않은 사용자 입니다.");
        }

        if (spreadMoney.getCreateDt().plusMinutes(10).compareTo(LocalDateTime.now()) < 0) {
            throw new ReceiveMoneyTimeOutException("뿌린지 10분이 지나 받을 수 없습니다.");
        }
    }

    private Money getSpreadMoney(String token) {
        return moneyCrudRepository.findByTokenAndMoneyTypeEnum(token, MoneyTypeEnum.SPREAD).orElse(null);
    }

    private List<Money> getReceivedMoneyUserNotInList(String token, User user) {
        return moneyCrudRepository.findAllByTokenAndMoneyTypeEnumAndUserIdNot(token, MoneyTypeEnum.RECEIVE, user.getUserId());
    }

    private List<Money> getReceivedMoneyList(String token) {
        return moneyCrudRepository.findAllByTokenAndMoneyTypeEnumAndUserIdIsNotNull(token, MoneyTypeEnum.RECEIVE);
    }

    private User getUser(Long userId) {
        return userCrudRepository.findByUserId(userId).orElse(null);
    }

    private Room getRoom(String roomId) {
        return roomCrudRepository.findByRoomId(roomId).orElse(null);
    }

    private Money createSpreadMoneyHistory(User user, Room room, int money, int userCount, String token) {
        return Money.builder()
                .moneyTypeEnum(MoneyTypeEnum.SPREAD)
                .room(room)
                .userId(user.getUserId())
                .token(token)
                .money(money)
                .userCount(userCount)
                .createDt(LocalDateTime.now())
                .updateDt(LocalDateTime.now())
                .build();
    }

    private List<Money> createReceiveMoneyHistory(User user, Room room, int money, int userCount, String token) {
        List<Money> receiveMoneyHistoryList = new ArrayList<>();

        for (int i = 0; i < userCount; i++) {

            int distributedMoney = money / userCount;

            if (i == userCount - 1) {
                distributedMoney += money % userCount;
            }

            Money receiveMoney = Money.builder()
                    .moneyTypeEnum(MoneyTypeEnum.RECEIVE)
                    .room(room)
                    .userId(user.getUserId())
                    .token(token)
                    .money(distributedMoney)
                    .userCount(1)
                    .createDt(LocalDateTime.now())
                    .updateDt(LocalDateTime.now())
                    .build();

            receiveMoneyHistoryList.add(receiveMoney);
            money = money - distributedMoney;
        }

        return receiveMoneyHistoryList;
    }
}