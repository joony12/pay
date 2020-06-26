package com.pay.infra.db;

import com.pay.domain.money.SpreadMoney;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.Optional;

public interface SpreadMoneyCrudRepository extends JpaRepository<SpreadMoney, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    Optional<SpreadMoney> findSpreadMoneyByToken(String token);

    Page<SpreadMoney> findSpreadMoneyByTokenAndSpreadStartTimeAfter(String token, LocalDateTime beforeTime, Pageable pageable);
}
