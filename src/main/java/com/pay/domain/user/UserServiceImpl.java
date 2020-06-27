package com.pay.domain.user;

import com.pay.infra.db.UserCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserCrudRepository userCrudRepository;

    @Override
    public User createUser(User user) {
        return userCrudRepository.save(user);
    }
}