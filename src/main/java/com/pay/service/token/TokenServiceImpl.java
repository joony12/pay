package com.pay.service.token;

import com.pay.entity.Money;
import com.pay.repository.MoneyCrudRepository;
import com.pay.util.encrypt.EncryptUtil;
import com.pay.util.type.MoneyTypeEnum;
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
    private final MoneyCrudRepository moneyCrudRepository;

    @Override
    @Transactional
    public String getToken(Long userId) {
        int maxNumber = 10;

        for (int i = 0; i < maxNumber; i++) {
            String token = createToken(userId);
            Money money = moneyCrudRepository.findByTokenAndMoneyTypeEnum(token, MoneyTypeEnum.SPREAD).orElse(null);

            if (ObjectUtils.isEmpty(money)) {
                return token;
            }
        }
        return StringUtils.EMPTY;
    }

    private String createToken(Long userId) {
        String plainText = String.format("%d_%d", System.currentTimeMillis(), userId);
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
