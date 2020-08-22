package com.pay.util.encrypt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class EncryptUtil {

    public String sha2(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytePhrase = StringUtils.getBytes(str, "UTF-8");
        MessageDigest md;
        byte[] mb;
        md = MessageDigest.getInstance("SHA-512");

        md.update(bytePhrase);
        mb = md.digest();
        return getHexString(mb);
    }

    private String getHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(Integer.toString((b & 0xF0) >> 4, 16)).append(Integer.toString(b & 0x0F, 16));
        }
        return result.toString();
    }
}
