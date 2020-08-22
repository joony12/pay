package com.pay.repository;

import com.pay.entity.Money;
import com.pay.util.type.MoneyTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MoneyCrudRepository extends JpaRepository<Money, Long> {
    Optional<Money> findByTokenAndMoneyTypeEnum(String token, MoneyTypeEnum moneyTypeEnum);

    Optional<Money> findByUserIdAndTokenAndMoneyTypeEnum(Long userId, String toke, MoneyTypeEnum moneyTypeEnum);

    List<Money> findAllByTokenAndMoneyTypeEnum(String token, MoneyTypeEnum moneyTypeEnum);

    List<Money> findAllByTokenAndMoneyTypeEnumAndUserIdIsNotNull(String token, MoneyTypeEnum moneyTypeEnum);
}
