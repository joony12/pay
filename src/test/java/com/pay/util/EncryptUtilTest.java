package com.pay.util;

import com.pay.util.encrypt.EncryptUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class EncryptUtilTest {

    @Test
    void encrypt() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        EncryptUtil encryptUtil = new EncryptUtil();
        String userId = "greenwood";
        String plainText = userId + System.currentTimeMillis();
        Assertions.assertNotNull(encryptUtil.sha2(plainText));
    }
}