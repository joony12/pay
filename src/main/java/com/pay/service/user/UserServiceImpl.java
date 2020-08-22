package com.pay.service.user;

import com.pay.entity.User;
import com.pay.repository.UserCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserCrudRepository userCrudRepository;

    @Override
    public User createUser(Long userId) {
        User user = new User();
        user.setUserId(userId);
        return userCrudRepository.save(user);
    }
}
