package com.pay.infra.db;

import com.pay.domain.money.ReceiveMoney;
import org.springframework.data.repository.CrudRepository;

public interface ReceiveMoneyCrudRepository extends CrudRepository<ReceiveMoney, Long> {

}
