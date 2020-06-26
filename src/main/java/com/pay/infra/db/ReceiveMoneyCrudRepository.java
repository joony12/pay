package com.pay.infra.db;

import com.pay.domain.money.ReceiveMoney;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ReceiveMoneyCrudRepository extends CrudRepository<ReceiveMoney, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    @Query(value = "update receive_money set receive_yn = true, receive_user_id = :userId, receive_time = :receiveTime", nativeQuery = true)
    void receiveMoney(String userId, LocalDateTime receiveTime);

    Optional<ReceiveMoney> findReceiveMoneyByReceiveUserId(String userId);
}
