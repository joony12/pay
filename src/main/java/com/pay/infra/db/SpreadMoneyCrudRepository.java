package com.pay.infra.db;

import com.pay.domain.money.SpreadMoney;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

public interface SpreadMoneyCrudRepository extends CrudRepository<SpreadMoney, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    Optional<SpreadMoney> findSpreadMoneyByToken(String token);
}
