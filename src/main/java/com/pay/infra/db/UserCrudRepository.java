package com.pay.infra.db;

import com.pay.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCrudRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByUserId(String userId);
}
