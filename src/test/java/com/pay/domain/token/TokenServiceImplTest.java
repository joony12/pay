package com.pay.domain.token;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class TokenServiceImplTest {

    @Autowired
    private TokenService tokenService;

    @Test
    void 토큰_생성() {
        String token = tokenService.getToken("user_01");
        Assert.assertEquals(token.length(), 3);
    }
}