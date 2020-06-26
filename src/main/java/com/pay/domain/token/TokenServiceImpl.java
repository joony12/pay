package com.pay.domain.token;

import com.pay.domain.money.SpreadMoney;
import com.pay.infra.db.SpreadMoneyCrudRepository;
import com.pay.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final EncryptUtil encryptUtil;
    private final SpreadMoneyCrudRepository spreadMoneyCrudRepository;

    @Override
    @Transactional
    public String getToken(String userId) {
        int maxNumber = 10;

        for (int i = 0; i < maxNumber; i++) {
            String token = createToken(userId);
            SpreadMoney spreadMoney = spreadMoneyCrudRepository.findSpreadMoneyByToken(token).orElse(null);

            if (ObjectUtils.isEmpty(spreadMoney)) {
                return token;
            }
        }
        return StringUtils.EMPTY;
    }

    private String createToken(String userId) {
        String plainText = System.currentTimeMillis() + userId;
        StringBuilder builder = new StringBuilder();
        try {
            String encryptText = encryptUtil.sha2(plainText);

            for (int i = 0; i < 3; i ++) {
                int randNumber = (int) ((Math.random() * 127) + 1);
                builder.append(encryptText.charAt(randNumber));
            }

            return builder.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            return StringUtils.EMPTY;
        }
    }
}
