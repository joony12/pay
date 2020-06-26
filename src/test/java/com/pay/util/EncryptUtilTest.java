package com.pay.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

class EncryptUtilTest {

    private EncryptUtil encryptUtil = new EncryptUtil();

    @Test
    void encrypt() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String userId = "greenwood";
        String plainText = userId + System.currentTimeMillis();
        Assertions.assertNotNull(encryptUtil.sha2(plainText));
    }
}